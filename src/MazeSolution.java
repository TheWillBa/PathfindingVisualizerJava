import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.nio.file.Path;
import java.util.*;

public class MazeSolution {
    public static void main(String[] args){
        int[][] maze = {
                {0,0,0,0,0,0},
                {1,1,1,1,1,0},
                {0,0,0,0,0,0},
                {0,1,1,1,1,1},
                {0,1,1,1,1,1},
                {0,0,0,0,0,0}
        };

        print2DArray(maze);

        int length = solution(maze);
        System.out.println("\nThe min length is: " + length);


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

    public static int solution(int[][] maze){
        scan(maze);
        PathSearch start = new PathSearch(new HashSet<>(), false, new Pos(0,0));
        Queue<PathSearch> todo = new LinkedList();
        todo.add(start);
        Set<Pos> path = solutionHelper(maze, todo);
        return path.size();
    }

    public static Set<Pos> solutionHelper(int[][] maze, Queue<PathSearch> todo){
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

            // Don't recurse if OOB, a wall, already reached here, or a potential skip and have skipped already
            if(val == -1 || val == 1 || path.contains(pos) || (val == 2 && hasSkipped)) continue;

            boolean skipped = val == 2;
            PathSearch ps = new PathSearch(new HashSet<Pos>(path), skipped, pos);
            todo.add(ps);
        }

        return solutionHelper(maze, todo);
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
