package main;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Environnement {
    private List<Cell> allCell;
    private renderEnvironnement render;
    private Map<String, Cell> cellMap;

    public Environnement(renderEnvironnement render) {
        this.render = render;
        this.allCell = Collections.synchronizedList(new ArrayList<>());
        this.cellMap = new ConcurrentHashMap<>();
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
        synchronized (allCell) {
            allCell.sort(this::compareByProbabilityOfType);
        }
    }

    private int compareByProbabilityOfType(Cell a, Cell b) {
        return Integer.compare(a.getProbabilityOfType(), b.getProbabilityOfType());
    }

    private void updateCellProbability(Cell lastChangedCell) {
        List<int[]> neighbors = lastChangedCell.getNeighbor();
        List<Cell> cellsToUpdate = new ArrayList<>();
        cellsToUpdate.add(lastChangedCell);

        for (int[] neighbor : neighbors) {
            Cell neighborCell = cellMap.get(Arrays.toString(neighbor));
            if (neighborCell != null) {
                cellsToUpdate.add(neighborCell);
            }
        }

        cellsToUpdate.parallelStream().forEach(c -> {
            List<Cell> neipo = new ArrayList<>();
            for (int[] n : c.getNeighbor()) {
                Cell neighborCell = cellMap.get(Arrays.toString(n));
                if (neighborCell != null && neighborCell.isCollapsed()) {
                    neipo.add(neighborCell);
                }
            }
            c.collapsed(neipo);
        });
    }

    private void createNeigh() {
        List<int[]> neighbor = allCell.get(0).getNeighbor();
        neighbor.parallelStream().forEach(n -> {
            if (!cellMap.containsKey(Arrays.toString(n))) {
                createCell(n[0], n[1]);
            }
        });
    }

    private void removeFullyCollapsedCells() {
        synchronized (allCell) {
            allCell.removeIf(cell -> cell.isCollapsed() && cell.getNeighbor().stream()
                    .allMatch(neighbor -> {
                        Cell neighborCell = cellMap.get(Arrays.toString(neighbor));
                        return neighborCell != null && neighborCell.isCollapsed();
                    }));
            cellMap.entrySet().removeIf(entry -> !allCell.contains(entry.getValue()));
        }
    }

    public void start() {
        this.render.render();
        Cell lastChangedCell = null;
        while (true) {
            if (lastChangedCell != null) {
                this.updateCellProbability(lastChangedCell);
            }
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
                lastChangedCell = cellToCollapse;
            }

            for (Cell c : this.allCell) {
                this.render.setImagesPosition(c.getCellId()[0] * 3, c.getCellId()[1] * 3, c.getType());
            }
        }
    }
}