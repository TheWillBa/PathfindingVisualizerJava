import java.awt.*;

public class GridCreator {
    int width;
    int height;

    int[][] map;

    public GridCreator(int w, int h){
        width = w;
        height = h;
        clear();
    }

    public void clear(){
        map = new int[width][height];
    }

    public void flip(int x, int y){
        // Don't do anything oob
        if(x < 0 || x >= width || y < 0 || y >= height) return;
        map[x][y] = (map[x][y] + 1) % 2;
    }

    public int[][] map(){
        return map;
    }
}
