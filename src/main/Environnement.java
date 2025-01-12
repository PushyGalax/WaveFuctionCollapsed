package main;

import java.awt.*;
import java.util.ArrayList;

public class Environnement {
    private ArrayList<Cell> allCell;

    public Environnement() {
        this.allCell = new ArrayList<>();
        Cell c = new Cell(0,0,this);
        this.allCell.add(c);

    }

    private void createCell(int x, int y) {
        Cell c = new Cell(x,y,this);
        this.allCell.add(c);
        c.start();
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
        this.allCell.sort(this::compareByProbabilityOfType);
    }

    private int compareByProbabilityOfType(Cell a, Cell b) {
        return Integer.compare(a.getProbabilityOfType(), b.getProbabilityOfType());
    }

//    public void killCell(Cell cell) {
//        this.notCollapsed.remove(cell);
//    }
}
