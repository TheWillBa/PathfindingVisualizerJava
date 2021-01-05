import java.util.List;

public abstract class AbstractSearchAlgorithm implements ShortestPathAlgorithm {
    protected int[][] map;
    protected GridPos start;
    protected GridPos end;
    protected List<? extends GridPos> path;

    /**
     * Returns true if the algorithms has finished
     *
     * @return true if done
     */
    @Override
    public boolean done() {
        return path != null;
    }

    /**
     * Returns the optimal path if the algorithm is done, null if none exists
     *
     * @return a <code>List</code> of <code>Pos</code>s that are the optimal path for the grid
     */
    @Override
    public List<? extends GridPos> path() {
        return path;
    }

    /**
     * Finishes the search so done becomes true
     */
    @Override
    public void finish() {
        while(!done()){
            tick();
        }
    }

    public int safeGet(int x, int y) {
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) {
            // In bounds
            return map[x][y];
        }

        return -1;
    }
}
