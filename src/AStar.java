import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;

public class AStar extends AbstractSearchAlgorithm {

    Set<PosH> closed;
    Queue<PosH> open;
    Set<PosH> openSet;
    Map<PosH, Integer> distances;

    public AStar(int[][] map, Pos start, Pos end){
        setMap(map, start, end);
    }


    /**
     * Sets the map that should be searched and the start and end pos
     *
     * @param map
     * @param start
     * @param end
     */
    @Override
    public void setMap(int[][] map, Pos start, Pos end) {
        this.map = map;
        this.start = start;
        this.end = end;

        distances = new HashMap<>();
        closed = new HashSet<>();
        openSet = new HashSet<>();
        open = new PriorityQueue<>();
        PosH s = new PosH(start);
        distances.put(s, 0);
        open.add(s);
    }

    /**
     * Returns the set of poses that are in line to be searched in the order they are to be searched
     *
     * @return the s
     */
    @Override
    public List<Pos> nextPoses() {
        return new ArrayList<>(open);
    }

    /**
     * Makes the next 'recursive' call in the algorithm
     */
    @Override
    public void next() {

        if(done()) return;

        PosH current = open.remove();
        closed.add(current);
        openSet.remove(current);


        int x = current.x;
        int y = current.y;

        if(x == end.x && y == end.y){
            backtrackPath();
            return;
        }

        for (double t = 0; t < Math.PI * 2; t += Math.PI / 2) {
            int i = (int) Math.round(Math.cos(t));
            int ii = (int) Math.round(Math.sin(t));
            int val = safeGet(i + x, ii + y);
            PosH pos = new PosH(x + i, y + ii);

            // Don't recurse if OOB, a wall, already reached here
            if (val == -1 || val == 1 || closed.contains(pos) || openSet.contains(pos))
                continue;


            open.add(pos);
            openSet.add(pos);
            // Leaving breadcrumbs
            distances.put(pos, distances.get(current) + 1);
        }

    }

    // TODO fix problems with f -> sum of g and h, g -> movement to this, h -> est. distance from goal scores

    private void backtrackPath() {
        LinkedList<PosH> bPath = new LinkedList<>();

        PosH n = new PosH(end);
        bPath.addFirst(n);
        int k = distances.get(n);

        PosH toAdd = null;
        while(!n.equals(start)){
            for(PosH p : distances.keySet()){
                if(!neighbors(p).contains(n)) continue;

                if(distances.get(p) < k){
                    bPath.addFirst(p);
                    n = p;
                    k--;
                    break;
                }
            }


        }

        path = bPath;
    }

    // TODO should reuse this elsewhere
    private Set<Pos> neighbors(Pos p) {

        Set<Pos> neighbors = new HashSet<Pos>();
        int x = p.x;
        int y = p.y;

        for (double t = 0; t < Math.PI * 2; t += Math.PI / 2) {
            int i = (int) Math.round(Math.cos(t));
            int ii = (int) Math.round(Math.sin(t));
            int val = safeGet(i + x, ii + y);
            PosH pos = new PosH(x + i, y + ii);

            // Don't recurse if OOB, a wall, already reached here
            if (val == -1 || val == 1)
                continue;

            neighbors.add(pos);

        }

        return neighbors;
    }


    private double heuristic(int x, int y){
        //return distanceFrom( x,  y, start) + distanceFrom(x, y, end);
        return distanceFrom( x,  y, start) + distanceFrom(x, y, end) /* * 1.1*/;
    }

    private int distanceFrom(int x, int y, Pos p) {
        // Manhattan distance, use euclidean if can move diagonal
        int dx = 10*Math.abs(x - p.x());
        int dy = 10*Math.abs(y - p.y());

        return dx + dy;

        //return (int) Math.sqrt(dx*dx + dx*dy);
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
