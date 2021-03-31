import java.util.ArrayList;
import java.util.Random;

// Checkstyle will report an error with this line:
import java.util.List;

public class SanityCheck {
    public static void main(String[] args) {
        // The following lines of code should compile only if you correctly installed Java 14 or higher.
        int n = new Random().nextInt(2);
        switch (n) {
            case 0 -> System.out.print("Java 14 or above is correctly installed: ");
            case 1 -> System.out.print("Java 14 or above is properly installed: ");
            default -> throw new IllegalStateException();
        }

        // The following four lines should run, but checkstyle should complain about style errors in each lines.
        // TODO: Checkstyle will flag all todos as errors
        // We use these to mark where we expect you to write code, so remember to remove them before submitting.
        ArrayList<String> a = new ArrayList<>();
        a.add("test"); // Deliberately long line: ............................................................................................... end
        System.out.println( !a.isEmpty());

        // The following line should produce an error when not commented.
        // List l = new ArrayList();

        System.out.println("Sanity check complete: everything seems to have been configured correctly!");
    }
}
