import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class VisualizerView extends Applet {
//    private int[][] maze;
//    private int side;
//    ShortestPathAlgorithm spa;
//
//    int xOffset = 0;
//    int yOffset = 175;
//
//    @Override
//    public void init(){
//        int[][] smallMaze = new int[][]{ // temp variable for use to implement drawing the grid
//                {0, 0, 0, 0, 0, 0},
//                {1, 0, 1, 1, 1, 0},
//                {0, 0, 0, 0, 0, 0},
//                {0, 1, 1, 0, 1, 1},
//                {0, 1, 1, 0, 1, 1},
//                {0, 0, 0, 0, 0, 0}
//        };
//
//        int[][] bigMaze = {
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
//                {1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0},
//                {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
//                {1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
//                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
//                {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
//        };
//
//
//        int[][] weirdMaze = {
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
//        };
//
//        int[][] uMaze = new int[][]{ // temp variable for use to implement drawing the grid
//                {0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 1, 0},
//                {0, 0, 0, 0, 1, 0},
//                {0, 0, 0, 0, 1, 0},
//                {0, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0}
//        };
//
//        maze = bigMaze;
//
//
//        side = getWidth() / maze.length;
//
//
//        spa = new AStar(
//                maze,
//                new Pos(0,0),
//                new Pos(maze.length - 1, maze[0].length - 1)
//        );
//
//
//
//
//    }
//
//    @Override
//    public void paint(Graphics g){
//
//        int delay = 500000/8;
//
//        /*
//        Draws the main grid
//         */
//        for(int i = 0; i < maze.length; ++i){
//            for(int j = 0; j < maze[0].length; ++j){
//                if(maze[i][j] == 0) drawCell(g, i, j, Color.white);
//                else drawCell(g, i, j, Color.black);
//            }
//        }
//
//        /*
//        Draw the path on the grid as it searches
//         */
//
//        Set<Pos> closed = new HashSet<>();
//        List<Pos> open = new ArrayList<>();
//
//        while(!spa.done()){
//
//
//
//            List<Pos> newPoses = spa.nextPoses();
//
//            for(Pos p : newPoses){
//                if(!closed.contains(p)){
//                    open.add(p);
//                }
//            }
//
//            int num = open.size();
//
//            for(Pos p : open){
//                Color c;
//                if(maze[p.x()][p.y()] != 0) c = Color.orange;
//                else c = Color.green;
//                drawCell(g, p.x(), p.y(), c);
//                delay(delay);
//            }
//
//            closed.addAll(open);
//            open.clear();
//
//            spa.next();
//
//        }
//
//        System.out.println("length: " + spa.path().size());
//
//
//
//        /*
//        Draw the final path
//         */
//
//        for(Pos p : spa.path()){
//            Color c;
//            if(maze[p.x()][p.y()] != 0) c = (Color.red);
//            else c = Color.magenta;
//
//            drawCell(g, p.x(), p.y(), c);
//
//            delay(delay/5);
//        }
//
//
//    }
//
//    public void drawCell(Graphics g, int x, int y, Color c){
//        Color pc = g.getColor();
//
//        g.setColor(c);
//        g.fillRect(y*side + xOffset, x*side + yOffset,side, side);
//        g.setColor(Color.black);
//        g.drawRect(y*side + xOffset, x*side + yOffset,side, side);
//
//        g.setColor(pc);
//    }
//
//    public void delay(long n)
//    {
//
//        n *= 1000;
//        long startDelay = System.nanoTime();
//        long endDelay = 0;
//        while (endDelay - startDelay < n)
//            endDelay = System.nanoTime();
//
//    }

    private int[][] maze;
    private int side;
    AStar spa;

    AStarStatic astar;

    int xOffset = 0;
    int yOffset = 175;

    @Override
    public void init(){
        int[][] smallMaze = new int[][]{ // temp variable for use to implement drawing the grid
                {0, 0, 0, 0, 0, 0},
                {1, 0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 1, 1},
                {0, 1, 1, 0, 1, 1},
                {0, 0, 0, 0, 0, 0}
        };

        int[][] bigMaze = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0},
                {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
                {0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };


        int[][] weirdMaze = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}

        };

        int[][] uMaze = new int[][]{ // temp variable for use to implement drawing the grid
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0}
        };

        maze = weirdMaze;


        side = getWidth() / maze.length;


        spa = new AStar(
                maze,
                new Pos(0,0),
                new Pos(maze.length - 1, maze[0].length - 1)
        );

        astar = new AStarStatic();
        astar.init(maze,
                new Pos(0,0),
                new Pos(5, 14));




    }

    @Override
    public void paint(Graphics g){

        int delay = 20000000/100;

        /*
        Draws the main grid
         */
        for(int i = 0; i < maze.length; ++i){
            for(int j = 0; j < maze[0].length; ++j){
                if(maze[i][j] == 0) drawCell(g, i, j, Color.white);
                else drawCell(g, i, j, Color.black);
            }
        }

        /*
        Draw the path on the grid as it searches
         */
/*
        Set<AStar.Node> closed = new HashSet<>();

        while(!spa.done()){



            List<Pos> newPoses = spa.nextPoses();

            for(int i = 0; i < newPoses.size(); ++i){
                Color c = Color.red;
                if(i == 0) c = Color.green;
                if(i != 0 && closed.contains((AStar.Node) newPoses.get(i))) continue;
                drawCell(g, (AStar.Node) newPoses.get(i), c);

                delay(delay);
            }

            for(Pos p : newPoses){
                closed.add((AStar.Node) p);
            }


            spa.next();

        }

        System.out.println("length: " + spa.path().size());
        System.out.println("num searched: " + spa.nodesChecked);

*/

        /*
        Draw the final path
         */

        /*
        Draw the path on the grid as it searches
         */
        Set<AStarStatic.Node> drawnClosed = new HashSet<>();
        Set<AStarStatic.Node> drawnOpen = new HashSet<>();

        while(!astar.done()){
            Set<AStarStatic.Node> open = astar.open();
            Set<AStarStatic.Node> closed = astar.closed();

            for(AStarStatic.Node node : closed){
                //if(drawnClosed.contains(node)) continue;
                drawCell(g, node, Color.green);
                //drawnClosed.add(node);
            }

            for(AStarStatic.Node node : open){
                //if(drawnOpen.contains(node)) continue;
                drawCell(g, node, Color.red);
                //drawnOpen.add(node);
            }

            Queue<AStarStatic.Node> queue = astar.queue();

            int count = 1;
            for(AStarStatic.Node node : queue){
                drawCell(g, node, Color.lightGray);
                g.drawString(Integer.toString(count++), node.y*side + xOffset + side/2, node.x*side + yOffset + (int) (side * (3.0 / 4.0)));
            }

            System.out.println("Looking at: " + queue.peek());

            delay(delay/5);
            astar.tick();
        }


        for(AStarStatic.Node p : astar.path()){
            Color c;
            if(maze[p.x()][p.y()] != 0) c = (Color.red);
            else c = Color.magenta;

            drawCell(g,  p, c);


            delay(delay/5);
        }


    }

    public void drawCell(Graphics g, AStar.Node node, Color c){
        int x = node.x;
        int y = node.y;
        Color pc = g.getColor();

        g.setColor(c);
        g.fillRect(y*side + xOffset, x*side + yOffset,side, side);
        g.setColor(Color.black);
        g.drawRect(y*side + xOffset, x*side + yOffset,side, side);
        g.drawString(Integer.toString(node.fScore()), y*side + xOffset, x*side + yOffset + side/2);
        g.setColor(pc);
    }

    public void drawCell(Graphics g, AStarStatic.Node node, Color c){
        int x = node.x;
        int y = node.y;
        Color pc = g.getColor();

        g.setColor(c);
        g.fillRect(y*side + xOffset, x*side + yOffset,side, side);
        g.setColor(Color.black);
        g.drawRect(y*side + xOffset, x*side + yOffset,side, side);
        g.drawString(Integer.toString(node.fScore()), y*side + xOffset, x*side + yOffset + side/2);
        g.drawString(Integer.toString(node.hScore()), y*side + xOffset, x*side + yOffset + side);
        g.setColor(pc);
    }

        public void drawCell(Graphics g, int x, int y, Color c){
        Color pc = g.getColor();

        g.setColor(c);
        g.fillRect(y*side + xOffset, x*side + yOffset,side, side);
        g.setColor(Color.black);
        g.drawRect(y*side + xOffset, x*side + yOffset,side, side);

        g.setColor(pc);
    }

    public void delay(long n)
    {

        n *= 1000;
        long startDelay = System.nanoTime();
        long endDelay = 0;
        while (endDelay - startDelay < n)
            endDelay = System.nanoTime();

    }
}
