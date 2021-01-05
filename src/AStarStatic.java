import sun.awt.image.ImageWatched;

import java.util.*;

public class AStarStatic/* implements ShortestPathAlgorithm */{

    private int scalar = 10;

    int[][] map;
    Pos end;

    PriorityQueueSet<Node> open = new PriorityQueueSet<>();
    Set<Node> closed = new HashSet<>();

    List<Node> path = null;

    public Set<Node> open(){
        return open.set;
    }

    public Set<Node> closed() {
        return closed;
    }

    public Queue<Node> queue(){
        return open.queue;
    }

    public int safeGet(int x, int y) {
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) {
            // In bounds
            return map[x][y];
        }

        return -1;
    }

    public void init(int[][] map, Pos start, Pos end){
        this.map = map;
        this.end = end;

        open = new PriorityQueueSet<>();
        closed = new HashSet<>();
        open.add(new Node(start.x, start.y));
    }



    public void tick() {

        if(done()) return;

        Node current = open.dequeue();
        closed.add(current);

        if (current.equals(new Node(end.x, end.y))) {
            LinkedList<Node> path = new LinkedList<>();
            while (current != null) {
                path.addFirst(current);
                current = current.pred;
            }

            this.path = path;
            return;
        }
/*
        if(open.isEmpty()){
            this.path = new LinkedList<>();
            return;
        }*/


        for (Node neighbor : neighbors(current)) {
            if (closed.contains(neighbor)) continue;

            int temp_gScore = current.gScore() + scalar;

            if (temp_gScore < neighbor.gScore || !open.contains(neighbor)) {
                neighbor.gScore = temp_gScore;
                neighbor.pred = current;

                if (!open.contains(neighbor)) {
                    open.add(neighbor);
                }

            }

        }


    }

    public boolean done(){
        return path != null;
    }

    public List<Node> path(){
        return path;
    }

    /**
     * Finishes the search so done becomes true
     */

    public void finish() {
        while(!done()){
            tick();
        }
    }

    private List<Node> neighbors(Node current) {
        List<Node> r = new ArrayList<>();

        for (double t = 0; t < Math.PI * 2; t += Math.PI / 2) {
            int i = (int) Math.round(Math.cos(t));
            int ii = (int) Math.round(Math.sin(t));

            int x = current.x + i;
            int y = current.y + ii;

            int val = safeGet(x,y);

            if(val == 0)
                r.add(new Node(x, y));
        }

        return r;
    }

    private int manhattanDistanceFrom(int x, int y, Pos p) {
        // Manhattan distance, use euclidean if can move diagonal
        int dx = scalar*Math.abs(x - p.x());
        int dy = scalar*Math.abs(y - p.y());

        return dx + dy;

        //return (int) Math.sqrt(dx*dx + dx*dy);
    }

    private int euclideanDistanceFrom(int x, int y, Pos p) {
        int dx = Math.abs(x - p.x());
        int dy = Math.abs(y - p.y());

        return (int) (scalar * Math.sqrt(dx*dx + dy*dy));

    }


    public class Node extends Pos implements Comparable<Node> {

        private Node pred = null;

        private int gScore = 0;
        private final int hScore = manhattanDistanceFrom(x, y, end);
        private final int weight = 1;

        public Node(int x, int y) {
            super(x, y);
        }

        public Node(Pos p) {
            super(p.x, p.y);
        }

        @Override
        public int compareTo(Node p) {

            if (fScore() > p.fScore()) {
                return 1;
            } else if (fScore() < p.fScore()) {
                return -1;
            } else { // heuristic the same
                return Integer.compare(hScore(), p.hScore());
            }

        }

        /**
         * Used by the compareTo(), the sum of the f and g-score
         */
        public int fScore() {
            return gScore() + weight * hScore();
        }

        /**
         * A calculated distance from the starting node based on the path to get to this node
         */
        public int gScore() {
            return gScore;
        }

        /**
         * An estimate of the distance this Node is from the goal; a heuristic value
         */
        public int hScore() {
            return hScore;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "gScore=" + gScore +
                    ", hScore=" + hScore +
                    ", fScore=" + fScore() +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
/*
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Node node = (Node) o;
            return gScore == node.gScore &&
                    hScore == node.hScore;
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), gScore, hScore);
        }*/
    }

    private static class PriorityQueueSet<T> {
        private final PriorityQueue<T> queue = new PriorityQueue<>();
        private final Set<T> set = new HashSet<>();

        public void add(T o){
            queue.add(o);
            set.add(o);
        }

        public T dequeue(){
            T r = queue.poll();
            set.remove(r);
            return r;
        }

        public boolean contains(T o){
            return set.contains(o);
        }

        public boolean isEmpty(){
            return queue.isEmpty();
        }


    }
}
