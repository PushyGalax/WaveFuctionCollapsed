package main;

import java.awt.*;
import java.util.ArrayList;

public class Environnement {
    private ArrayList<Cell> allCell;

    private ArrayList<Cell> notCollapsed;

    public Environnement() {
        this.notCollapsed = new ArrayList<>();
        this.allCell = new ArrayList<>();
        Cell c = new Cell(0,0,this);
        this.allCell.add(c);
        this.notCollapsed.add(c);
    }

    private void createCell(int x, int y) {
        Cell c = new Cell(x,y,this);
        this.allCell.add(c);
        this.notCollapsed.add(c);
        c.start();
    }

    public ArrayList<Cell> getAlive() {
        return this.notCollapsed;
    }

    public boolean isCellCollapsed(int[] pos) {
        boolean result = false;
        for (Cell c : this.allCell) {
            if (c.getCellId() == pos) {
                if (c.isCollapsed()) result=true; break;
            }
        }
        return result;
    }

    private void sortAliveCellsByProbability() {
        this.notCollapsed.sort(this::compareByProbabilityOfType);
    }

    private int compareByProbabilityOfType(Cell a, Cell b) {
        return Integer.compare(a.getProbabilityOfType(), b.getProbabilityOfType());
    }

    public void killCell(Cell cell) {
        this.notCollapsed.remove(cell);
    }
}
