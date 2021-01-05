import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class VisualizerView extends JApplet {
    private int[][] maze;
    private int side;

    private AStar astar;

    private int xOffset = 0;
    private int yOffset = 175;

    Set<HeuristicNode> closed = new HashSet<>();
    Queue<HeuristicNode> queue = new PriorityQueue<>();

    boolean running = false;



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
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
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
                {0, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 1, 0}
        };

        maze = smallMaze;


        side = getWidth() / maze.length;




        Button button1 = new Button("Start");
        setLayout(null);
        button1.setBounds(0,0, 50, 20);
        add(button1);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!running){
                    running = true;
                    repaint();
                }
            }
        });


    }


    @Override
    public void paint(Graphics g) {

        super.paint(g);
        /*
        Draws the main grid
         */
        for (int i = 0; i < maze.length; ++i) {
            for (int j = 0; j < maze[0].length; ++j) {
                if (maze[i][j] == 0) drawCell(g, i, j, Color.white);
                else drawCell(g, i, j, Color.black);
            }
        }

        if(running){
              /*
        Draw the path on the grid as it searches
         */
            astar = new AStar();
            astar.init(maze,
                    new GridPos(0,0),
                    //new GridPos(5,14)
                    new GridPos(maze.length - 1, maze[0].length - 1)
            );


            for (int u = 0; !astar.done(); astar.tick()) {

                queue = astar.queue();
                closed = astar.closed();





                int num = queue.size();
                for (int i = 0; i < num; ++i) {
                    HeuristicNode node = queue.remove();
                    drawCell(g, node, Color.red);
                    g.drawString(Integer.toString(i), node.y() * side + xOffset + side / 2, node.x() * side + yOffset + (int) (side * (3.0 / 4.0)));
                }

                for (HeuristicNode node : closed) {
                    drawCell(g, node, Color.green);
                }
                delay(200000);
            }




        /*
        Draw the final path
         */
            for (HeuristicNode p : astar.path()) {
                Color c;
                if (maze[p.x()][p.y()] != 0) c = (Color.red);
                else c = Color.magenta;

                drawCell(g, p, c);
                delay(200000 / 5);
            }

        }




    }

    public void drawCell(Graphics g, HeuristicNode node, Color c){
        int x = node.x();
        int y = node.y();
        Color pc = g.getColor();

        g.setColor(c);
        g.fillRect(y*side + xOffset, x*side + yOffset,side, side);
        g.setColor(Color.black);
        g.drawRect(y*side + xOffset, x*side + yOffset,side, side);
        g.drawString(Integer.toString(node.fScore()), y*side + xOffset, x*side + yOffset + side/2);
        g.drawString(Integer.toString(node.hScore()), y*side + xOffset, x*side + yOffset + side);
        g.drawString(Integer.toString(node.gScore()), y*side + xOffset + side/2, x*side + yOffset + side);
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

    public void delay(long n) {
        n *= 1000;
        long startDelay = System.nanoTime();
        long endDelay = 0;
        while (endDelay - startDelay < n)
            endDelay = System.nanoTime();
    }

}
