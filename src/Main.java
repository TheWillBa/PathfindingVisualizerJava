import java.util.List;

public class Main {
    public static void main(String[] args){
        int[][] maze = {
                {0,0,0,0,0,0},
                {1,1,1,1,1,0},
                {0,0,0,0,0,0},
                {0,1,1,1,1,1},
                {0,1,1,1,1,1},
                {0,0,0,0,0,0}
        };

        ShortestPathAlgorithm spa = new ModifiedBFS(
                maze,
                new Pos(0,0),
                new Pos(maze.length - 1, maze[0].length - 1)
        );


        while(!spa.done()){
            spa.next();
        }

        List<Pos> path = spa.path();

        System.out.println("length: " + path.size());

    }
}
