import java.util.*;


/**
 * DOES NOT FIND THE OPTIMAL PATH< JUST SKIPS THE FIRST WALL THAT IT CAN
 */
public class ModifiedBFSPlusAStar extends AbstractSearchAlgorithm {

    private Queue<PathSearch> todo;
    private TrackerCell[][] tracker;

    public ModifiedBFSPlusAStar(int[][] map, Pos start, Pos end){
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
        PathSearch startPs = new PathSearch(new HashSet<>(), new LinkedList<>(), false, new PosH(this.start));
        todo = new PriorityQueue<>();
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

        Set<PosH> pathSoFar = current.pathSoFar;
        int x = current.current.x();
        int y = current.current.y();
        boolean hasSkipped = current.hasSkipped;

        PosH newPos = new PosH(x, y);
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
            PosH pos = new PosH(x + i, y + ii);

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

            PathSearch ps = new PathSearch(new HashSet<PosH>(pathSoFar), new LinkedList<>(current.listSoFar),skipped, pos);
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

    private static class PathSearch implements Comparable<PathSearch> {
        Set<PosH> pathSoFar;
        List<PosH> listSoFar;
        boolean hasSkipped;
        PosH current;

        public PathSearch(Set<PosH> pathSoFar, List<PosH> listSoFar, boolean hasSkipped, PosH current) {
            this.pathSoFar = pathSoFar;
            this.hasSkipped = hasSkipped;
            this.current = current;
            this.listSoFar = listSoFar;
        }

        public Pos current(){
            return current;
        }


        @Override
        public int compareTo(PathSearch o) {
            return current.compareTo(o.current);
        }
    }



    private double heuristic(int x, int y){
        //return distanceFrom( x,  y, start) + distanceFrom(x, y, end);
        return distanceFrom( x,  y, start) + distanceFrom(x, y, end) /* * 1.1*/;
    }

    private int distanceFrom(int x, int y, Pos p) {
        // Manhattan distance, use euclidean if can move diagonal
        int dx = Math.abs(x - p.x());
        int dy = Math.abs(y - p.y());

        return dx + dy;
    }


    private class PosH extends Pos implements Comparable<Pos>{

        public PosH(int x, int y) {
            super(x, y);
        }

        public PosH(Pos p){
            super(p.x, p.y);
        }

        @Override
        public int compareTo(Pos p) {
            if(this.equals(p)) return 0;


            if(heuristic(x, y) > heuristic(p.x, p.y)){
                return 1;
            }
            else if(heuristic(x, y) < heuristic(p.x, p.y)){
                return -1;
            }
            else { // heuristic the same
                if(distanceFrom(x, y, end) > distanceFrom(p.x,  p.y, end)){
                    return 1;
                }
                else if(distanceFrom(x, y, end) < distanceFrom(p.x,  p.y, end)){
                    return -1;
                }
                else return 0;
            }

        }


    }




}
