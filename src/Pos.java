import java.util.Objects;

public class Pos {
    private int x;
    private int y;

    public Pos(int x, int y) {
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
        if (!(o instanceof Pos)) return false;
        Pos p = (Pos) o;
        return p.x == x && p.y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
