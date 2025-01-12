package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Environnement {
    private ArrayList<Cell> allCell;
    private renderEnvironnement render;
    private Map<String, Cell> cellMap;

    public Environnement(renderEnvironnement render) {
        this.render = render;
        this.allCell = new ArrayList<>();
        this.cellMap = new HashMap<>();
        Cell c = new Cell((int)Math.round(Math.random()*10),(int)Math.round(Math.random()*10));
        this.allCell.add(c);
        this.cellMap.put(Arrays.toString(c.getCellId()), c);
        this.start();
    }

    private void createCell(int x, int y) {
        Cell c = new Cell(x, y);
        this.allCell.add(c);
        this.cellMap.put(Arrays.toString(c.getCellId()), c);
    }

    private void sortAliveCellsByProbability() {
        this.allCell.sort(this::compareByProbabilityOfType);
    }

    private int compareByProbabilityOfType(Cell a, Cell b) {
        return Integer.compare(a.getProbabilityOfType(), b.getProbabilityOfType());
    }

    private void updateCellProbability() {
        for (Cell c : this.allCell) {
            ArrayList<Cell> neipo = new ArrayList<>();
            for (int[] n : c.getNeighbor()) {
                Cell neighborCell = cellMap.get(Arrays.toString(n));
                if (neighborCell != null && neighborCell.isCollapsed()) {
                    neipo.add(neighborCell);
                }
            }
            c.collapsed(neipo);
        }
    }

    private void createNeigh() {
        ArrayList<int[]> neighbor = this.allCell.get(0).getNeighbor();
        for (int[] n : neighbor) {
            if (!cellMap.containsKey(Arrays.toString(n))) {
                createCell(n[0], n[1]);
            }
        }
    }

    private void removeFullyCollapsedCells() {
        allCell.removeIf(cell -> cell.isCollapsed() && cell.getNeighbor().stream()
                .allMatch(neighbor -> {
                    Cell neighborCell = cellMap.get(Arrays.toString(neighbor));
                    return neighborCell != null && neighborCell.isCollapsed();
                }));
        cellMap.entrySet().removeIf(entry -> !allCell.contains(entry.getValue()));
    }

    public void start() {
        this.render.render();
        while (true) {
            this.updateCellProbability();
            this.sortAliveCellsByProbability();
            this.createNeigh();
            this.removeFullyCollapsedCells();

            // Select the cell with the lowest probability to collapse
            Cell cellToCollapse = this.allCell.stream()
                    .filter(c -> !c.isCollapsed())
                    .findFirst()
                    .orElse(null);

            if (cellToCollapse != null) {
                cellToCollapse.setType();
            }

            for (Cell c : this.allCell) {
                this.render.setImagesPosition(c.getCellId()[0] * 3, c.getCellId()[1] * 3, c.getType());
            }

            // Wait
            int count = 0;
            for (Cell c : this.allCell) {
                if (c.isCollapsed()) {
                    count++;
                }
            }
        }
    }
}