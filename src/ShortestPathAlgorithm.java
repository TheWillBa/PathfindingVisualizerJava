import java.util.*;

public interface ShortestPathAlgorithm {

    /**
     * Sets the map that should be searched and the start and end pos
     */
    void setMap(int[][] map, Pos start, Pos end);

    /**
     * Returns the set of poses that are in line to be searched in the order they are to be searched
     * @return the s
     */
    Pos[] nextPoses();

    /**
     * Makes the next 'recursive' call in the algorithm
     */
    void next();

    /**
     * Returns true if the algorithms has finished
     * @return true if done
     */
    boolean done();

    /**
     * Returns the optimal path if the algorithm is done
     * @return a <code>List</code> of <code>Pos</code>s that are the optimal path for the grid
     */
    List<Pos> path();



}
