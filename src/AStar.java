import java.util.*;
import java.util.function.Function;

public class AStar/* implements ShortestPathAlgorithm */{

    private int scalar = 10;
    private int weight = 1;

    private int[][] map;
    private Pos end;
    private Pos start;

    private PriorityQueueSet<Node> open = new PriorityQueueSet<>();
    private Set<Node> closed = new HashSet<>();

    private List<Node> path = null;

    private final DistanceFunction d = (Pos p1, Pos p2) -> (euclideanDistanceFrom(p1.x(), p1.y(), p2));
    private final NeighborFunction nf = this::diagNeighbors;

    public Set<HeuristicNode> closed() {
        return new HashSet<>(closed);
    }
    public Queue<HeuristicNode> queue(){
        return new PriorityQueue<>(open.queue);
    }

    private Map<Pos, Integer> gScores;
    private Map<Pos, Node> predecessors;

    public void setWeight(int w){
        weight = w;
    }

    public Pos start(){
        return start;
    }

    public Pos end(){
        return end;
    }

    public int safeGet(int x, int y) {
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) {
            // In bounds
            return map[x][y];
        }

        return -1;
    }

    public void reset(){
        init(map, start, end);
    }

    public void init(int[][] map, Pos start, Pos end){
        this.map = map;
        this.end = end;
        this.start = start;

        open = new PriorityQueueSet<>();
        closed = new HashSet<>();
        open.add(new Node(start.x(), start.y()));

        gScores = new HashMap<>();
        gScores.put(new Node(start.x(), start.y()), 0);
        predecessors = new HashMap<>();
    }



    public void tick() {

        if(done()) return;

        if(open.isEmpty()){
            this.path = new LinkedList<>();
            System.out.println("no path");
            return;
        }

        Node current = open.dequeue();
        closed.add(current);

        if (current.equals(new Node(end.x(), end.y()))) {
            LinkedList<Node> path = new LinkedList<>();
            while (current != null) {
                path.addFirst(current);
                current = current.pred();
            }

            this.path = path;
            return;
        }


        for (Node neighbor : nf.neighbors(current)) {
            if (closed.contains(neighbor)) continue;

            int temp_gScore = gScores.get(current) + d.distance(current, neighbor);

            if (!open.contains(neighbor) || temp_gScore < gScores.get(neighbor)) {
                gScores.put(neighbor, temp_gScore);
                neighbor.set_gScore(temp_gScore);
                neighbor.setPred(current);
                open.add(neighbor);
            }

        }


    }

    public boolean done(){
        return path != null;
    }

    public List<HeuristicNode> path(){
        return new ArrayList<HeuristicNode>(path);
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

        // The order that this returns the neighbors in effects the bias direction of the search
        for (double t = 0; t > - Math.PI * 2; t -= Math.PI / 2) {
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

    private List<Node> diagNeighbors(Node current) {
        List<Node> r = new ArrayList<>();

        // The order that this returns the neighbors in effects the bias direction of the search
        for(int i = -1; i <= 1; ++i){
            for(int ii = -1; ii <= 1; ++ii){
                int x = current.x + i;
                int y = current.y + ii;
                int val = safeGet(x,y);
                if(val == 0)
                    r.add(new Node(x, y));
            }
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


    public class Node extends GridPos implements Comparable<Node>, HeuristicNode {

        private int gScore= 0;
        private final int hScore = d.distance(new GridPos(x, y), end);

        public Node(int x, int y) {
            super(x, y);
        }

        public Node(Pos p) {
            super(p.x(), p.y());
        }

        @Override
        public int compareTo(Node p) {

            if (fScore() > p.fScore()) {
                return 1;
            } else if (fScore() < p.fScore()) {
                return -1;
            } else {
                return 0;

                // This return makes the algorithm more greedy towards the goal
                //return Integer.compare(hScore(), p.hScore());
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

        public void set_gScore(int gScore){
            this.gScore = gScore;
            gScores.put(this, gScore);
        }

        public Node pred(){
            return predecessors.get(this);
        }

        public void setPred(Node pred){
            predecessors.put(this, pred);
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
                    "gScore=" + gScore() +
                    ", hScore=" + hScore() +
                    ", fScore=" + fScore() +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
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
            return set.isEmpty();
        }


    }

    @FunctionalInterface
    private interface DistanceFunction{
        int distance(Pos start, Pos end);
    }

    @FunctionalInterface
    private interface NeighborFunction{
        List<Node> neighbors(Node n);
    }
}
