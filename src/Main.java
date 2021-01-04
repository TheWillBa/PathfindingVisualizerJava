import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args){
        /*int[][] maze = {
                {0,0,0,0,0,0},
                {1,0,1,1,1,0},
                {0,0,0,0,0,0},
                {0,1,1,0,1,1},
                {0,1,1,0,1,1},
                {0,0,0,0,0,0}
        };

        AStar spa = new AStar(
                maze,
                new Pos(0,0),
                new Pos(maze.length - 1, maze[0].length - 1)
        );

        System.out.println(spa.done());
        spa.finish();
        System.out.println(spa.done());

        AStar.Node[][] nodes = spa.nodes;

        for(AStar.Node[] row : nodes){
            for(AStar.Node node : row){
                if (node.fScore() == Double.POSITIVE_INFINITY) System.out.print("oooo ");
                else System.out.print(node.fScore() + " ");
            }
            System.out.println("");
        }

        System.out.println("");

        for(AStar.Node[] row : nodes){
            for(AStar.Node node : row){
                System.out.print(node.hScore() + " ");
            }
            System.out.println("");
        }

        Map<Integer, Integer> map = new HashMap<>();
        map.put(5,5);
        map.put(5,6);


        System.out.print(map.get(5));*/



        AStar a = new AStar(new int[5][5], new Pos(0,0), new Pos(4,4));
        a.next();
        a.next();
        a.next();
        a.next();




        System.out.println(a.nextPoses());

        




    }
}
