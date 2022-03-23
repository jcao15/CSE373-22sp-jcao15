package edu.washington.cse373.experiments;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.LongUnaryOperator;

/**
 * A chart that can plot the output of functions as they are run, ideal for functions that take a
 * significant amount of time to compute.
 */

/*
Don't bother trying to figure out how this code works. UIs are far outside of the scope of this
course, and you should not need to understand any of this code to analyze the experiments.
 */
public class PlotWindow extends JFrame {

    private final String title;
    private final String xAxisLabel;
    private final String yAxisLabel;
    private final XYSeries[] series;
    private final XYSeriesCollection seriesCollection;
    private final BiFunction<TriConsumer<Integer, Long, Long>, Runnable, TrialsRunner> trialFactory;
    private TrialsRunner trialsRunner;
    private int runCount;
    private XYItemRenderer plotRenderer;
    private Crosshair yCrosshair;
    private Crosshair xCrosshair;
    private JMenuItem rerunMenuItem;

    public PlotWindow(String title, String xAxisLabel, String yAxisLabel,
                      LongUnaryOperator[] functions, String[] names, List<Long> values) {
        this(title, xAxisLabel, yAxisLabel, names,
                (onUpdate, onDone) -> new SingleTrialsRunner(functions, values, onUpdate, onDone));
        checkArrayLengthsEqual(functions.length, names.length);
    }

    public PlotWindow(String title, String xAxisLabel, String yAxisLabel,
                      LongUnaryOperator[] functions, String[] names, List<Long> values, long atol, double rtol) {
        this(title, xAxisLabel, yAxisLabel, names,
                (onUpdate, onDone) -> new AveragedTrialsRunner(functions, values, atol, rtol, onUpdate, onDone));
        checkArrayLengthsEqual(functions.length, names.length);
    }

    private void checkArrayLengthsEqual(int numFunctions, int numNames) {
        if (numFunctions != numNames) {
            throw new IllegalArgumentException(String.format(
                    "Number of functions (%d) does not match number of series names (%d).", numFunctions, numNames));
        }
    }

    private PlotWindow(String title, String xAxisLabel, String yAxisLabel, String[] names,
                       BiFunction<TriConsumer<Integer, Long, Long>, Runnable, TrialsRunner> trialFactory) {
        super(title);
        this.title = title;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.trialFactory = trialFactory;

        this.series = createSeries(names);
        this.seriesCollection = new XYSeriesCollection();
        for (XYSeries s : series) {
            this.seriesCollection.addSeries(s);
        }

        this.runCount = 0;
    }

    /**
     * Construct and launch a new plotting window, and start plotting the outputs of the given
     * functions (without repeating trials with the same values).
     */
    public static void launch(String title, String xAxisLabel, String yAxisLabel,
                              LongUnaryOperator[] functions, String[] names, List<Long> values) {
        SwingUtilities.invokeLater(() -> {
            PlotWindow window = new PlotWindow(title, xAxisLabel, yAxisLabel, functions, names, values);
            window.runTrial();
            window.construct();
        });
    }

    /**
     * Construct and launch a new plotting window, and start plotting the outputs of the given
     * functions averaged over multiple trials.
     *
     * Requires additional parameters for absolute tolerance (atol) and relative tolerance (rtol)
     * for determining how many trials to run.
     */
    /*
    In all of our experiments, atol/vtol are essentially just magic numbers chosen to make the plots
    look sufficiently smooth.
     */
    public static void launch(String title, String xAxisLabel, String yAxisLabel,
                              LongUnaryOperator[] functions, String[] names, List<Long> values,
                              long atol, double rtol) {
        SwingUtilities.invokeLater(() -> {
            PlotWindow window = new PlotWindow(title, xAxisLabel, yAxisLabel, functions, names, values, atol, rtol);
            window.runTrial();
            window.construct();
        });
    }

    private static XYSeries[] createSeries(String[] names) {
        return Arrays.stream(names).map(XYSeries::new).toArray(XYSeries[]::new);
    }

    /**
     * Initializes and shows the window.
     */
    public void construct() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException |
                UnsupportedLookAndFeelException | ClassNotFoundException e) {
            // Ignore
        }
        this.createChartPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void createChartPanel() {
        JFreeChart jfreechart = ChartFactory.createXYLineChart(
                title, xAxisLabel, yAxisLabel, seriesCollection);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();

        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setVerticalTickLabels(true);

        this.plotRenderer = xyPlot.getRenderer();

        ChartPanel chartPanel = new ChartPanel(jfreechart);
        chartPanel.setDefaultDirectoryForSaveAs(new File(System.getProperty("user.dir")));

        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(Double.NaN, Color.DARK_GRAY, XYPlot.DEFAULT_CROSSHAIR_STROKE);
        this.yCrosshair = new Crosshair(Double.NaN, Color.DARK_GRAY, XYPlot.DEFAULT_CROSSHAIR_STROKE);
        crosshairOverlay.addDomainCrosshair(this.xCrosshair);
        crosshairOverlay.addRangeCrosshair(this.yCrosshair);
        chartPanel.addOverlay(crosshairOverlay);

        this.addMenuItems(chartPanel);

        this.add(chartPanel, BorderLayout.CENTER);
    }

    private void addMenuItems(ChartPanel chartPanel) {
        JPopupMenu contextMenu = chartPanel.getPopupMenu();

        contextMenu.addSeparator();

        this.rerunMenuItem = new JMenuItem("Run again to smooth plot");
        this.rerunMenuItem.setEnabled(!this.isTrialRunning()); // possible race condition?
        this.rerunMenuItem.addActionListener(e -> runTrial());
        contextMenu.add(this.rerunMenuItem);

        // rename context menu item for chart properties
        ((JMenuItem) contextMenu.getComponent(0)).setText("Chart Properties...");

        // construct popup window for series properties
        JPanel seriesPropertiesPanel = new JPanel(new GridBagLayout());
        List<JTextField> textFields = new ArrayList<>();
        List<JCheckBox> checkBoxes = new ArrayList<>();
        XYSeries[] xySeries = this.series;
        for (int i = 0; i < xySeries.length; i++) {
            GridBagConstraints c = new GridBagConstraints();
            c.ipadx = 5;
            c.fill = GridBagConstraints.HORIZONTAL;

            JLabel seriesLabel = new JLabel((String) xySeries[i].getKey());
            c.gridx = 0;
            c.gridy = i * 3;
            c.gridwidth = 2;
            c.ipady = 10;
            seriesPropertiesPanel.add(seriesLabel, c);

            c.ipady = 3;

            JLabel textFieldLabel = new JLabel("Name:");
            c.gridx = 0;
            c.gridy = i * 3 + 1;
            c.gridwidth = 1;
            c.weightx = 0.2;
            seriesPropertiesPanel.add(textFieldLabel, c);

            JTextField textField = new JTextField(10);
            c.gridx = 1;
            c.gridy = i * 3 + 1;
            c.gridwidth = 1;
            c.weightx = 1;
            seriesPropertiesPanel.add(textField, c);

            JLabel checkBoxLabel = new JLabel("Show:");
            c.gridx = 0;
            c.gridy = i * 3 + 2;
            c.gridwidth = 1;
            c.weightx = 0.2;
            seriesPropertiesPanel.add(checkBoxLabel, c);

            JCheckBox checkBox = new JCheckBox("", true);
            c.gridx = 1;
            c.gridy = i * 3 + 2;
            c.gridwidth = 1;
            c.weightx = 1;
            seriesPropertiesPanel.add(checkBox, c);

            textFields.add(textField);
            checkBoxes.add(checkBox);
        }

        // add context menu item for series properties
        JMenuItem seriesPropertiesMenuItem = new JMenuItem("Series Properties...");
        seriesPropertiesMenuItem.addActionListener(e -> {
            // reset popup window
            for (int i = 0; i < this.series.length; i++) {
                XYSeries s = this.series[i];
                String name = (String) s.getKey();
                textFields.get(i).setText(name);

                // getSeriesVisible returns null if visibility was never set
                Boolean visibleWrapper = this.plotRenderer.getSeriesVisible(i);
                boolean visible = visibleWrapper == null ? true : visibleWrapper;
                checkBoxes.get(i).setSelected(visible);
            }
            // show popup window
            int result = JOptionPane.showConfirmDialog(null, seriesPropertiesPanel,
                    "Series Properties", JOptionPane.OK_CANCEL_OPTION);
            // process user response
            if (result == JOptionPane.OK_OPTION) {
                // we need to remove all series first, since the collection won't allow us to change
                // keys of series that it contains
                this.seriesCollection.removeAllSeries();
                for (int i = 0; i < textFields.size(); i++) {
                    JTextField textField = textFields.get(i);
                    String newName = textField.getText();
                    this.series[i].setKey(newName);
                    this.seriesCollection.addSeries(this.series[i]);
                    this.plotRenderer.setSeriesVisible(i, checkBoxes.get(i).isSelected());
                }
            }
        });
        contextMenu.insert(seriesPropertiesMenuItem, 1);
    }

    /**
     * Starts running whatever trial this window was constructed with. After the window is
     * constructed, new values will show up automatically. (This method may be called before or
     * after calling `construct`.)
     * Does nothing if a trial is already running.
     * If results for a previous run exists, averages new values with existing ones.
     */
    public void runTrial() {
        if (isTrialRunning()) {
            return;
        }
        // since we're allowing this method to run before the UI is constructed, we need to do null checks
        if (this.rerunMenuItem != null) {
            this.rerunMenuItem.setEnabled(false);
        }
        this.runCount++;
        double factor = 1.0 / this.runCount;
        this.trialsRunner = this.trialFactory.apply(
                (seriesIndex, value, result) -> {
                    XYSeries s = this.series[seriesIndex];
                    int index = s.indexOf(value);
                    if (index >= 0) {
                        double prevResult = s.getY(index).doubleValue();
                        s.update(value, prevResult * (1 - factor) + result * factor);
                    } else {
                        s.add(value, result);
                    }
                    if (this.yCrosshair != null) {
                        this.xCrosshair.setValue(value);
                        this.yCrosshair.setValue(result);
                    }
                },
                () -> {
                    if (this.rerunMenuItem != null) {
                        this.rerunMenuItem.setEnabled(true);
                        this.xCrosshair.setValue(Double.NaN);
                        this.yCrosshair.setValue(Double.NaN);
                    }
                }
        );
        this.trialsRunner.execute();
    }

    /**
     * Returns whether a trial is currently running.
     */
    public boolean isTrialRunning() {
        return this.trialsRunner != null && this.trialsRunner.getState() == SwingWorker.StateValue.STARTED;
    }

    private abstract static class TrialsRunner extends SwingWorker<Long, Long[]> {
        final LongUnaryOperator[] functions;
        final List<Long> values;
        final TriConsumer<Integer, Long, Long> onUpdate;
        final Runnable onDone;

        TrialsRunner(LongUnaryOperator[] functions, List<Long> values,
                     TriConsumer<Integer, Long, Long> onUpdate, Runnable onDone) {
            this.functions = functions;
            this.values = values;
            this.onUpdate = onUpdate;
            this.onDone = onDone;
        }

        @Override
        protected Long doInBackground() {
            long start = System.currentTimeMillis();
            values.forEach(value -> {
                for (int i = 0; i < functions.length; i++) {
                    LongUnaryOperator function = functions[i];
                    long result = runTrial(function, value);
                    publish(new Long[]{(long) i, value, result});
                }
            });
            return System.currentTimeMillis() - start;
        }

        protected abstract long runTrial(LongUnaryOperator function, long value);

        @Override
        protected void process(List<Long[]> chunks) {
            for (Long[] chunk : chunks) {
                int seriesNumber = chunk[0].intValue();
                long value = chunk[1];
                long result = chunk[2];
                this.onUpdate.accept(seriesNumber, value, result);
            }
        }

        @Override
        protected void done() {
            try {
                System.out.println("Plotting finished in " + this.get() / 1000. + " seconds.");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            onDone.run();
        }
    }

    private static class AveragedTrialsRunner extends TrialsRunner {
        private final long atol;
        private final double rtol;

        AveragedTrialsRunner(LongUnaryOperator[] functions, List<Long> values, long atol, double rtol,
                             TriConsumer<Integer, Long, Long> onUpdate, Runnable onDone) {
            super(functions, values, onUpdate, onDone);
            this.atol = atol;
            this.rtol = rtol;
        }

        protected long runTrial(LongUnaryOperator function, long value) {
            // Run an extra time to warm up caches
            long result = function.applyAsLong(value);
            int k = 1;
            while (true) {
                long v = function.applyAsLong(value);
                long newResult = (result + v) / 2;
                if (k > 10 || approximatelyEqual(result, newResult, atol, rtol)) {
                    return newResult;
                }
                k++;
            }
        }

        private static boolean approximatelyEqual(long v1, long v2, long atol, double rtol) {
            long difference = Math.abs(v1 - v2);
            return difference < atol || difference < rtol * Math.abs(v1);
        }
    }

    private static class SingleTrialsRunner extends TrialsRunner {
        SingleTrialsRunner(LongUnaryOperator[] functions, List<Long> values,
                           TriConsumer<Integer, Long, Long> onUpdate, Runnable onDone) {
            super(functions, values, onUpdate, onDone);
        }

        protected long runTrial(LongUnaryOperator function, long value) {
            return function.applyAsLong(value);
        }
    }
}
