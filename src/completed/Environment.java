package completed;

import java.util.ArrayList;

public class Environment {
    private int width;
    private int height;
    private ArrayList<Cell> cells = new ArrayList<>();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<Cell> cellSort = new ArrayList<>();

    public Environment(int width, int height, ArrayList<Tile> tiles) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ArrayList<Integer> possibilities = new ArrayList<>();
                for (int k = 0; k < tiles.size(); k++) {
                    possibilities.add(tiles.get(k).getId());
                }
                cells.add(new Cell(i, j, possibilities, tiles));
            }
        }
        cellSort = new ArrayList<>(cells);

        // get random cell and collapse it by tacking a random tile in his possibilities
        int randomCell = (int) (Math.random() * cells.size());
        Cell cell = cells.get(randomCell);
        int randomTile = (int) (Math.random() * cell.getPossibilities().size());
        cell.setTile(tiles.get(cell.getPossibilities().get(randomTile)));
        ArrayList<Integer> color = new ArrayList<>();
        color.add(123);
        color.add(154);
        color.add(234);
        cell.setColor();
        cell.collapse();
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    private void makePossibilityCell() {
        for (int i = 0; i < cellSort.size(); i++) {
            Cell cell = cellSort.get(i);
            if (!cell.isCollapsed()) {
                int x = cell.getX();
                int y = cell.getY();
                ArrayList<Integer> possibilities = new ArrayList<>(cell.getPossibilities());

                if (x > 0) {
                    Cell left = cellSort.get((x - 1) * height + y);
                    if (left.isCollapsed()) {
                        possibilities.retainAll(left.getTile().getPossibilityRight());
                    }
                }
                if (x < width - 1) {
                    Cell right = cellSort.get((x + 1) * height + y);
                    if (right.isCollapsed()) {
                        possibilities.retainAll(right.getTile().getPossibilityLeft());
                    }
                }
                if (y > 0) {
                    Cell up = cellSort.get(x * height + (y - 1));
                    if (up.isCollapsed()) {
                        possibilities.retainAll(up.getTile().getPossibilityDown());
                    }
                }
                if (y < height - 1) {
                    Cell down = cellSort.get(x * height + (y + 1));
                    if (down.isCollapsed()) {
                        possibilities.retainAll(down.getTile().getPossibilityUp());
                    }
                }

                cell.setPossibilities(possibilities);
            }
        }
    }

    private void sortCell() {
        //sort by length of possibilities
        for (int i = 0; i < cellSort.size(); i++) {
            for (int j = i + 1; j < cellSort.size(); j++) {
                if (cellSort.get(i).isCollapsed()) {
                    continue;
                }
                if (cells.get(i).getPossibilities().size() > cells.get(j).getPossibilities().size()) {
                    Cell temp = cellSort.get(i);
                    cellSort.set(i, cellSort.get(j));
                    cellSort.set(j, temp);
                }
            }
        }
    }

    private Tile collapseCell(Cell cell) {
        // look throw the neighbors tile and look for the possibilities that are in common and finally return a random tile from the possibilities
        ArrayList<Integer> possibilities = new ArrayList<>(cell.getPossibilities());
        int x = cell.getX();
        int y = cell.getY();
        if (x > 0) {
            Cell left = cellSort.get((x - 1) * height + y);
            if (left.isCollapsed()) {
                possibilities.retainAll(left.getTile().getPossibilityRight());
            }
        }
        if (x < width - 1) {
            Cell right = cellSort.get((x + 1) * height + y);
            if (right.isCollapsed()) {
                possibilities.retainAll(right.getTile().getPossibilityLeft());
            }
        }
        if (y > 0) {
            Cell up = cellSort.get(x * height + (y - 1));
            if (up.isCollapsed()) {
                possibilities.retainAll(up.getTile().getPossibilityDown());
            }
        }
        if (y < height - 1) {
            Cell down = cellSort.get(x * height + (y + 1));
            if (down.isCollapsed()) {
                possibilities.retainAll(down.getTile().getPossibilityUp());
            }
        }
        int randomTile = (int) (Math.random() * possibilities.size());
        return tiles.get(possibilities.get(randomTile));
    }

    public void update() {
        sortCell();
        makePossibilityCell();

        Cell actualCell = cellSort.get(0);
        actualCell.setTile(collapseCell(actualCell));
        for (int i = 0; i < cellSort.size(); i++) {
            if (cellSort.get(i).isCollapsed()) {
                cellSort.remove(cellSort.get(i));
            }
        }
        for (Cell cell : cells) {
            cell.update();
        }

    }
}
