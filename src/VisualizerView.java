import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VisualizerView extends Applet {
    private int[][] maze;
    private int side;
    private static List<Pos> path;

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
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        maze = smallMaze;


        side = getWidth() / maze.length;


        ShortestPathAlgorithm spa = new ModifiedBFS(
                maze,
                new Pos(0,0),
                new Pos(maze.length - 1, maze[0].length - 1)
        );


        while(!spa.done()){
            spa.next();
        }

        path = spa.path();
    }

    @Override
    public void paint(Graphics g){
        int xOffset = 0;
        int yOffset = 175;

        /*
        Draws the main grid
         */
        for(int i = 0; i < maze.length; ++i){
            for(int j = 0; j < maze[0].length; ++j){
                if(maze[i][j] == 0) g.setColor(Color.white);
                else g.setColor(Color.black);

                g.fillRect(j*side + xOffset, i*side + yOffset,side, side);
            }
        }

        /*
        Draw the path on the grid
         */
        g.setColor(Color.MAGENTA);
        for(Pos p : path){
            if(maze[p.x()][p.y()] != 0) g.setColor(Color.red);
            g.fillRect(p.y()*side + xOffset, p.x()*side + yOffset, side, side);
            if(maze[p.x()][p.y()] != 0) g.setColor(Color.MAGENTA);
        }


        /*
        Draws black borders around each grid square
         */
        for(int i = 0; i < maze.length; ++i){
            for(int j = 0; j < maze[0].length; ++j){
                g.setColor(Color.black);
                g.drawRect(j*side + xOffset, i*side + yOffset,side, side);

            }
        }
    }
}
