package grosser.prototype.map;
/**
 * @author Ben
 * Class to represent a position of a tile.
 */

public class TilePosition {

    private final int x, y;


    TilePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TilePosition && (((TilePosition) o).x == this.x) && (((TilePosition) o).y == this.y);
    }

    @Override
    public int hashCode() {
        return x*y;
    }
}
