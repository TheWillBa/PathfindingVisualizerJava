import java.awt.*;

public class GridCreator {
    int width;
    int height;

    final int[][] map;

    public GridCreator(int w, int h){
        width = w;
        height = h;
        map = new int[width][height];
    }

    public void clear(){
        for(int i = 0; i < map.length; ++i){
            for(int j = 0; j < map[0].length; ++j){
                map[i][j] = 0;
            }
        }
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
