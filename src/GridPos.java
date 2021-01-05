import java.util.Objects;

public class GridPos implements Pos{
    protected int x;
    protected int y;

    public GridPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GridPos)) return false;
        GridPos p = (GridPos) o;
        return p.x == x && p.y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Pos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
