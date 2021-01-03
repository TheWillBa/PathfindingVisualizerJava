import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class VisualizerView extends Applet {
    private int[][] maze;
    private int side;
    ShortestPathAlgorithm spa;

    int xOffset = 0;
    int yOffset = 175;

    @Override
    public void init(){
        int[][] smallMaze = new int[][]{ // temp variable for use to implement drawing the grid
                {0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0}
        };

        int[][] bigMaze = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        maze = bigMaze;


        side = getWidth() / maze.length;


        spa = new AStar(
                maze,
                new Pos(0,0),
                new Pos(maze.length - 1, maze[0].length - 1)
        );




    }

    @Override
    public void paint(Graphics g){

        int delay = 500000/4;

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

        Set<Pos> closed = new HashSet<>();
        List<Pos> open = new ArrayList<>();

        while(!spa.done()){

            List<Pos> newPoses = spa.nextPoses();

            for(Pos p : newPoses){
                if(!closed.contains(p)){
                    open.add(p);
                }
            }

            for(Pos p : open){
                Color c;
                if(maze[p.x()][p.y()] != 0) c = Color.orange;
                else c = Color.green;
                drawCell(g, p.x(), p.y(), c);
                delay(delay);
            }

            closed.addAll(open);
            open.clear();

            spa.next();

        }

        System.out.println("length: " + spa.path().size());



        /*
        Draw the final path
         */

        for(Pos p : spa.path()){
            Color c;
            if(maze[p.x()][p.y()] != 0) c = (Color.red);
            else c = Color.magenta;

            drawCell(g, p.x(), p.y(), c);

            delay(delay);
        }


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
