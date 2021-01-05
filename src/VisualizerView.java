import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class VisualizerView extends Applet implements MouseListener, MouseMotionListener {
    private int[][] maze;
    private int side;

    private AStar astar;

    private int xOffset = 0;
    private int yOffset = 175;

    Set<HeuristicNode> closed = new HashSet<>();
    Queue<HeuristicNode> queue = new PriorityQueue<>();

    boolean running = false;

    int width = 15;
    int height = 15;

    GridCreator gc = new GridCreator(width, height);

    Pos start = new GridPos(0,0);
    Pos end = new GridPos(width - 1, height - 1);

    int weight = 1;

    int delay = 10000;



    @Override
    public void init(){
        /*
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

        int[][] emptyMaze = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] uMaze = new int[][]{ // temp variable for use to implement drawing the grid
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 1, 0}
        };

        */

        maze = gc.map();
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

        Button resetButton = new Button("Reset Path");
        resetButton.setBounds(50,0, 80, 20);
        add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    running = false;
                    if(astar != null)
                        astar.reset();
                    repaint();
                }
            }
        );

        Button clearButton = new Button("Clear");
        clearButton.setBounds(130,0, 50, 20);
        add(clearButton);
        clearButton.addActionListener(new ActionListener() {
                                          @Override
                                          public void actionPerformed(ActionEvent e) {
                                              gc.clear();
                                              repaint();
                                          }
                                      }
        );

        addMouseListener(this);
        addMouseMotionListener(this);
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

        /*
        Draws the start and end points
         */
        drawCell(g, start.x(), start.y(), Color.yellow);
        drawCell(g, end.x(), end.y(), Color.orange);



        if(running){
              /*
        Draw the path on the grid as it searches
         */
            astar = new AStar();
            astar.init(maze, start, end);
            astar.setWeight(weight);

            for (int u = 0; !astar.done(); astar.tick()) {

                queue = astar.queue();
                closed = astar.closed();

                Set<HeuristicNode> drawn = new HashSet<>();

                for (HeuristicNode node : closed) {
                    drawCell(g, node, Color.green);
                    drawn.add(node);
                }


                int num = queue.size();
                for (int i = 0; i < num; ++i) {
                    HeuristicNode node = queue.remove();

                    if(drawn.contains(node)) continue;

                    drawCell(g, node, Color.red);
                    g.drawString(Integer.toString(i), node.y() * side + xOffset + side / 2, node.x() * side + yOffset + (int) (side * (3.0 / 4.0)));
                }

                HeuristicNode front = astar.queue().peek();
                if(front != null)
                    drawCell(g, front, Color.blue);

                delay(delay);
            }




        /*
        Draw the final path
         */
            for (HeuristicNode p : astar.path()) {
                Color c;
                if (maze[p.x()][p.y()] != 0) c = (Color.red);
                else c = Color.magenta;

                drawCell(g, p, c);
                delay(delay / 5);
            }

        }

        running = false;
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

    public Pos canvasToGridPosition(Pos p){
        int y = (int) Math.floor((p.x() - xOffset) / (side * 1.0));
        int x = (int) Math.floor((p.y() - yOffset) / (side * 1.0));
        return new GridPos(x,y);
    }

    public void delay(long n) {
        n *= 1000;
        long startDelay = System.nanoTime();
        long endDelay = 0;
        while (endDelay - startDelay < n)
            endDelay = System.nanoTime();
    }


    private Pos lastPos = new GridPos(-1,-1);

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {

        Pos p = canvasToGridPosition(new GridPos(e.getX(), e.getY()));
        int x = p.x();
        int y = p.y();


        if(start.equals(new GridPos(x,y)) || end.equals(new GridPos(x,y)) ) return;

        if(lastPos.equals(p)) return;

        gc.flip(x,y);
        lastPos = p;
        repaint();
        e.consume();
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        Pos p = canvasToGridPosition(new GridPos(e.getX(), e.getY()));
        int x = p.x();
        int y = p.y();

        if(start.equals(new GridPos(x,y)) || end.equals(new GridPos(x,y)) ) return;

        gc.flip(x,y);
        repaint();
        e.consume();
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
