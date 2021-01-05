import java.util.*;

public interface ShortestPathAlgorithm {

    /**
     * Sets the map that should be searched and the start and end pos
     */
    void init(int[][] map, GridPos start, GridPos end);

    /**
     * Returns the set of poses that are in line to be searched in the order they are to be searched
     * @return the s
     */
    List<GridPos> nextPoses();

    /**
     * Makes the next 'recursive' call in the algorithm
     */
    void tick();

    /**
     * Returns true if the algorithms has finished
     * @return true if done
     */
    boolean done();

    /**
     * Returns the optimal path if the algorithm is done
     * @return a <code>List</code> of <code>Pos</code>s that are the optimal path for the grid
     */
    List<? extends GridPos> path();

    /**
     * Finishes the search so done becomes true
     */
    void finish();



}
