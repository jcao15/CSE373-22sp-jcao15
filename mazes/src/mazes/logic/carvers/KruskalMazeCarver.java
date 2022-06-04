package mazes.logic.carvers;

import graphs.EdgeWithData;
import graphs.minspantrees.MinimumSpanningTreeFinder;
import mazes.entities.Room;
import mazes.entities.Wall;
import mazes.logic.MazeGraph;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Carves out a maze based on Kruskal's algorithm.
 */
public class KruskalMazeCarver extends MazeCarver {
    MinimumSpanningTreeFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder;
    private final Random rand;

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random();
    }

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder,
                             long seed) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random(seed);
    }

    /*
        - By randomizing the wall weights, we remove random walls which satisfy criterion 1.
        - An MST, by definition, will include a path from every vertex (every room) to every other one,
        satisfying criterion 2.
        - And finally, because the MST will not have cycles,
        we avoid removing unnecessary edges and end up with a maze where there really is only one solution,
        satisfying criterion 3.
     */
    @Override
    protected Set<Wall> chooseWallsToRemove(Set<Wall> walls) {

        Set<EdgeWithData<Room, Wall>> edges = new HashSet<>();
        Set<Wall> updatedWalls = new HashSet<>();

        for (Wall wall : walls) {
            //Randomizing the wall weights
            double weight = rand.nextDouble();
            EdgeWithData<Room, Wall> newEdge = new EdgeWithData<>(wall.getRoom1(), wall.getRoom2(), weight, wall);
            edges.add(newEdge);
        }
        //Create an MST after removing random walls
        HashSet<EdgeWithData<Room, Wall>> newbie = (HashSet<EdgeWithData<Room, Wall>>)
            this.minimumSpanningTreeFinder.findMinimumSpanningTree(new MazeGraph(edges)).edges();
        // Hint: you'll probably need to include something like the following:

        for (EdgeWithData<Room, Wall> wall : newbie) {
            updatedWalls.add(wall.data());
        }

        return updatedWalls;

    }
}
