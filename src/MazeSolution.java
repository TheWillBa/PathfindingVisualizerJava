import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.nio.file.Path;
import java.util.*;

public class MazeSolution {
    private static int NUM_CALLS_TO_HELPER = 0;

    public static void main(String[] args){

        int[][] maze = {
                {0,0,0,0,0,0},
                {1,1,1,1,1,0},
                {0,0,0,0,0,0},
                {0,1,1,1,1,1},
                {0,1,1,1,1,1},
                {0,0,0,0,0,0}
        };

/*
        int n = 8;
        int[][] maze = new int[n][n];

        for(int i = 0; i <= n-1; i++){
            for(int j = 0; j <= n-1; j++){
                if(Math.random() < 0.5)
                maze[i][j] = 1;
            }
        }

        maze[0][0] = 0;
        maze[n-1][n-1] = 0;*/



        print2DArray(maze);

        Set<Pos> set = solution(maze);
        System.out.println("\nThe min length is: " + set.size() + "");
        System.out.println("Num calls: " + NUM_CALLS_TO_HELPER + "\n");

        for(Pos p : set){
            maze[p.x][p.y] = 3;
        }

        print2DArray(maze);



    }

    public static class TrackerCell{
        boolean tVisit;
        boolean fVisit;
        boolean visitComplete(){
            return fVisit && tVisit;
        }
    }

    public static class Pos{
        int x;
        int y;
        public Pos(int x, int y){
            this.x = x;
            this.y = y;
        }
        @Override
        public boolean equals(Object o){
            if(!(o instanceof Pos)) return false;
            Pos p = (Pos) o;
            return p.x == x && p.y == y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

    }

    public static class PathSearch{
        Set<Pos> pathSoFar;
        boolean hasSkipped;
        Pos current;

        public PathSearch(Set<Pos> pathSoFar, boolean hasSkipped, Pos current) {
            this.pathSoFar = pathSoFar;
            this.hasSkipped = hasSkipped;
            this.current = current;
        }

    }

    public static Set<Pos> solution(int[][] map) {
        scan(map);
        PathSearch start = new PathSearch(new HashSet<>(), false, new Pos(0, 0));
        Queue<PathSearch> todo = new LinkedList<>();
        todo.add(start);
        TrackerCell[][] tracker = new TrackerCell[map.length][map[0].length];
        for(int i = 0; i < tracker.length; ++i){
            for(int j = 0; j < tracker[i].length; ++j){
                tracker[i][j] = new TrackerCell();
            }
        }
        Set<Pos> path = solutionHelper(map, tracker, todo);
        return path;
    }

    public static Set<Pos> solutionHelper(int[][] maze, TrackerCell[][] tracker, Queue<PathSearch> todo){
        NUM_CALLS_TO_HELPER++;
        PathSearch current = todo.remove();
        Set<Pos> path = current.pathSoFar;
        int x = current.current.x;
        int y = current.current.y;
        boolean hasSkipped = current.hasSkipped;

        path.add(new Pos(x,y));
        if(x == maze.length - 1 && y == maze[0].length - 1) return path;

        for(double t = 0; t < Math.PI * 2; t += Math.PI / 2){
            int i = (int) Math.round(Math.cos(t));
            int ii = (int) Math.round(Math.sin(t));
            int val = safeGet(i + x, ii + y, maze);
            Pos pos = new Pos(x + i, y + ii);
            if(val == -1) continue;
            // Don't recurse if OOB, a wall, already reached here, or a potential skip and have skipped already
            if(val == -1 || val == 1 || path.contains(pos) || (val == 2 && hasSkipped) || tracker[pos.x][pos.y].visitComplete())
                continue;

            tracker[pos.x][pos.y].tVisit = hasSkipped;

            boolean skipped = val == 2;

            PathSearch ps = new PathSearch(new HashSet<Pos>(path), skipped, pos);
            todo.add(ps);
        }

        return solutionHelper(maze, tracker, todo);
    }

    /**
     * Scans the maze and flags any walls that could potentially be removed, setting them equal to 2
     * @param maze
     */
    public static void scan(int[][] maze){

        for(int i = 0; i < maze.length; ++i){
            for(int ii = 0; ii < maze[i].length; ++ii){

                // Only scan walls
                if(maze[i][ii] == 0) continue;

                int oCount = 0;

                // Look around each wall in the four cardinal directions
                for(double t = 0; t < Math.PI * 2; t += Math.PI / 2){
                    int x = (int) Math.round(Math.cos(t));
                    int y = (int) Math.round(Math.sin(t));

                    if(safeGet(i + x, ii + y, maze) == 0){
                        ++oCount;
                    }
                }

                if(oCount >= 2) {
                    maze[i][ii] = 2;
                }

            }
        }
    }

    public static int safeGet(int x, int y, int[][] maze){
        if(x >= 0 && x < maze.length && y >= 0 && y < maze[0].length){
            // In bounds
            return maze[x][y];
        }

        return -1;
    }

    public static void print2DArray(int[][] arr){
        for (int[] ints : arr) {
            System.out.println(Arrays.toString(ints));
        }
    }
}
