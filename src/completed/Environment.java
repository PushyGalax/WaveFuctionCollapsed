package completed;

import java.util.ArrayList;

public class Environment {
    private int width;
    private int height;
    private ArrayList<Cell> cells = new ArrayList<>();
    private ArrayList<Cell> cellsUnCollapsed = new ArrayList<>();
    private ArrayList<Tile> tiles;

    public Environment(int width, int height, ArrayList<Tile> tiles) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells.add(new Cell(i, j));
            }
        }
    }
}
