package completed;

public class Cell {
    private int x;
    private int y;
    private boolean collapsed;
    private Tile tile;
    private int[] color;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.collapsed = false;
        this.tile = null;
        this.color = null;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public void collapse() {
        this.collapsed = true;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }

    public Tile getTile() {
        return this.tile;
    }
}
