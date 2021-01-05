import java.util.*;

public class ModifiedBFS extends AbstractSearchAlgorithm {

    private Queue<PathSearch> todo;
    private TrackerCell[][] tracker;

    public ModifiedBFS(int[][] map, Pos start, Pos end){
        init(map, start, end);
    }

    /**
     * Sets the map that should be searched and the start and end pos
     *
     * @param map the grid to be searched
     * @param start the <code>Pos</code> on the grid to start searching from
     * @param end the goal <code>Pos</code> on the grid
     */
    @Override
    public void init(int[][] map, Pos start, Pos end) {
        this.map = map;
        this.start = start;
        this.end = end;
        scanMap();
        PathSearch startPs = new PathSearch(new HashSet<>(), new LinkedList<>(), false, this.start);
        todo = new LinkedList<>();
        todo.add(startPs);
        tracker = new TrackerCell[map.length][map[0].length];
        for (int i = 0; i < tracker.length; ++i) {
            for (int j = 0; j < tracker[i].length; ++j) {
                tracker[i][j] = new TrackerCell();
            }
        }
        path = null;
    }

    /**
     * Returns the set of poses that are in line to be searched in the order they are to be searched
     *
     * @return the s
     */
    @Override
    public List<Pos> nextPoses() {
        ArrayList<Pos> poses = new ArrayList<>();
        int i = 0;
        for(PathSearch ps : todo){
            poses.add(ps.current);
        }
        return poses;
    }

    /**
     * Makes the next 'recursive' call in the algorithm
     */
    @Override
    public void tick() {

        if(done()) return;

        PathSearch current = todo.remove();

        Set<Pos> pathSoFar = current.pathSoFar;
        int x = current.current.x();
        int y = current.current.y();
        boolean hasSkipped = current.hasSkipped;

        Pos newPos = new Pos(x, y);
        pathSoFar.add(newPos);
        current.listSoFar.add(newPos);

        if (x == end.x() && y == end.y()) {
            path = current.listSoFar;
            return;
        }

        // Loop through the adjacent cells
        for (double t = 0; t < Math.PI * 2; t += Math.PI / 2) {
            int i = (int) Math.round(Math.cos(t));
            int ii = (int) Math.round(Math.sin(t));
            int val = safeGet(i + x, ii + y);
            Pos pos = new Pos(x + i, y + ii);

            // Don't recurse if OOB, a wall, already reached here, or a potential skip and have skipped already
            if (val == -1 || val == 1 || pathSoFar.contains(pos) || (val == 2 && hasSkipped))
                continue;

            TrackerCell tc = tracker[pos.x()][pos.y()];

            // If this path has skipped a wall and we've visited this having skipped aleady, don't recurse
            if (hasSkipped && tc.tVisit) continue;
            // If this path has not skipped a wall and we've visited this not having skipped aleady, don't recurse
            if (!hasSkipped && tc.fVisit) continue;

            if (hasSkipped) {
                tc.tVisit = true;
            } else {
                tc.fVisit = true;
            }


            boolean skipped;

            if(!hasSkipped){
                skipped = val == 2;
            }
            else{
                skipped = true;
            }

            PathSearch ps = new PathSearch(new HashSet<Pos>(pathSoFar), new LinkedList<>(current.listSoFar),skipped, pos);
            todo.add(ps);
        }
    }

    /**
     * Scans the map and flags any walls that could potentially be removed, setting them equal to 2
     */
    private void scanMap() {

        for (int i = 0; i < map.length; ++i) {
            for (int ii = 0; ii < map[i].length; ++ii) {

                // Only scan walls
                if (map[i][ii] == 0) continue;

                int oCount = 0;

                // Look around each wall in the four cardinal directions
                for (double t = 0; t < Math.PI * 2; t += Math.PI / 2) {
                    int x = (int) Math.round(Math.cos(t));
                    int y = (int) Math.round(Math.sin(t));

                    if (safeGet(i + x, ii + y) == 0) {
                        ++oCount;
                    }
                }

                if (oCount >= 2) {
                    map[i][ii] = 2;
                }

            }
        }
    }

    private static class TrackerCell {
        boolean tVisit;
        boolean fVisit;
    }

    private static class PathSearch {
        Set<Pos> pathSoFar;
        List<Pos> listSoFar;
        boolean hasSkipped;
        Pos current;

        public PathSearch(Set<Pos> pathSoFar, List<Pos> listSoFar, boolean hasSkipped, Pos current) {
            this.pathSoFar = pathSoFar;
            this.hasSkipped = hasSkipped;
            this.current = current;
            this.listSoFar = listSoFar;
        }

        public Pos current(){
            return current;
        }
    }




}
