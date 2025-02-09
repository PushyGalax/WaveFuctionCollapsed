package completed;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Cell {
    private int x;
    private int y;
    private boolean collapsed;
    private ArrayList<Tile> tiles;
    private Tile tile;
    private ArrayList<Integer> possibilities;
    private ArrayList<Integer> color = new ArrayList<>();

    public Cell(int x, int y, ArrayList<Integer> possibilities, ArrayList<Tile> tiles) {
        this.x = x;
        this.y = y;
        this.collapsed = false;
        this.tile = null;
        this.possibilities = possibilities;
        this.tiles = tiles;
        setColor();
    }



    public void setColor() {
        if (this.tile != null) {
            this.color = tile.getColor();
        } else {
            this.color = new ArrayList<>();
            this.color.add(255);
            this.color.add(255);
            this.color.add(255);
        }
    }

    public ArrayList<Integer> getColor() {
        return this.color;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public List<Integer> getPossibilities() {
        return possibilities;
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

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setPossibilities(ArrayList<Integer> possibilities) {
        this.possibilities = possibilities;
    }

    public void update() {
        if (!this.collapsed) {
            if (this.possibilities.size() == 1) {
                //replace with random tile in the possibilities
                this.tile = tiles.get(this.possibilities.get((int) (Math.random() * this.possibilities.size())));
                this.collapsed = true;
            }
        }
    }
}
