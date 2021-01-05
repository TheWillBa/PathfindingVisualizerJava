import java.util.*;

public class AStar extends AbstractSearchAlgorithm {
    public int nodesChecked = 0;

    int scalar = 10;

    PriorityQueue<Node> open;
    Set<Node> openSet;
    public Node[][] nodes;
    Set<Node> closed;

    public AStar(int[][] map, Pos start, Pos end){
        init(map, start, end);
    }


    /**
     * Sets the map that should be searched and the start and end pos
     *
     * @param map
     * @param start
     * @param end
     */
    @Override
    public void init(int[][] map, Pos start, Pos end) {
        this.map = map;
        this.start = start;
        this.end = end;

        nodes = new Node[map.length][map[0].length];

        for(int i = 0; i < nodes.length; ++i){
            for(int j = 0; j < nodes[0].length; ++j){
                nodes[i][j] = new Node(i,j);
            }
        }

        openSet = new HashSet<>();
        open = new PriorityQueue<>();


        Node s = new Node(start);
        s.gScore = 0;

        nodes[s.x][s.y] = s;

        open.add(s);
        openSet.add(s);

        closed = new HashSet<>();


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
    public void tick() {

        if (done()) return;

        if (openSet.contains(nodes[end.x][end.y])) {
            System.out.println("Finishing...");
            backtrackPath();
            return;
        }

        ++nodesChecked;

        Node current = open.remove();
        openSet.remove(current);
        closed.add(current);

        int x = current.x;
        int y = current.y;


        System.out.println("Current: " + current);



        /*for (int i = -1; i <= 1; i++) {
            for(int ii = -1; ii <=1 ; ii++){*/

        for (double t = 0; t < Math.PI * 2; t += Math.PI / 2) {
            int i = (int) Math.round(Math.cos(t));
            int ii = (int) Math.round(Math.sin(t));

            int val = safeGet(i + x, ii + y);

            // Don't recurse if OOB or a wall
            if (val == -1 || val == 1 || closed.contains(new Node(i + x, ii + y)))
                continue;

            Node pos = nodes[x + i][y + ii];

            double temp_gScore = current.gScore() + scalar /*euclideanDistanceFrom(0,0, new Pos(i,ii))*/; // In this case the distance from current to pos is always 1,
            // So just add the scalar

            if (!openSet.contains(pos) || temp_gScore < pos.gScore) {
                if(!openSet.contains(pos)) {
                    open.add(pos);
                    openSet.add(pos);
                }
                pos.gScore = temp_gScore;

                // Leaving breadcrumbs
                pos.pred = current;
            }
            //}


        }

    }


    private void backtrackPath() {
        LinkedList<Node> bPath = new LinkedList<>();

        Node current = nodes[end.x][end.y];

        while(current != null) {
            bPath.addFirst(current);
            current = current.pred;
        }

        path = bPath;
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


    public class Node extends Pos implements Comparable<Node>{

        private Node pred = null;

        private double gScore = 0;
        private final double weight = 0;

        public Node(int x, int y) {
            super(x, y);
        }

        public Node(Pos p){
            super(p.x, p.y);
        }

        @Override
        public int compareTo(Node p) {

            if(fScore() > p.fScore()){
                return 1;
            }
            else if(fScore() < p.fScore()){
                return -1;
            }
            else { // heuristic the same
                if(hScore() < p.hScore()) return -1;
                else if(hScore() > p.hScore()) return 1;
                else return 0;
            }

        }

        /**
         * Used by the compareTo(), the sum of the f and g-score
         */
        public int fScore(){
            return gScore() + hScore();
        }

        /**
        A calculated distance from the starting node based on the path to get to this node
         */
        public int gScore(){
            return (int) gScore;
        }

        /**
        An estimate of the distance this PosH is from the goal; a heuristic value
         */
        public int hScore(){
            return manhattanDistanceFrom(x, y, end);
        }


    }


}
