package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Cell {
    // VARIABLES
    private int x;
    private int y;
    private final int[] UP;
    private final int[] RIGHT;
    private final int[] DOWN;
    private final int[] LEFT;

    private int[] id;

    private boolean collapsed;
    private int type = -1;

    private ArrayList<int[]> neighborg;
    private ArrayList<Integer> possibleType;

    // CONSTRUCTOR
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;

        this.neighborg = new ArrayList<>();
        this.id = new int[2];
        this.UP = new int[2];
        this.RIGHT = new int[2];
        this.DOWN = new int[2];
        this.LEFT = new int[2];

        this.collapsed = false;
        this.possibleType = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));

        this.id[0] = this.x;
        this.id[1] = this.y;

        this.initializeNeighbors();
    }

    // METHODS
    private void initializeNeighbors() {
        this.UP[0] = this.x - 1;
        this.UP[1] = this.y;

        this.RIGHT[0] = this.x;
        this.RIGHT[1] = this.y + 1;

        this.DOWN[0] = this.x + 1;
        this.DOWN[1] = this.y;

        this.LEFT[0] = this.x;
        this.LEFT[1] = this.y - 1;

        this.neighborg.add(this.UP);
        this.neighborg.add(this.RIGHT);
        this.neighborg.add(this.DOWN);
        this.neighborg.add(this.LEFT);
    }

    public int[] getCellId() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }

    public ArrayList<Integer> getPossibleType() {
        return this.possibleType;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }

    public ArrayList<int[]> getNeighbor() {
        return this.neighborg;
    }

    public int getProbabilityOfType() {
        if (this.collapsed) {
            return 10;
        } else {
            return this.possibleType.size();
        }
    }

    public void setType() {
        if (this.possibleType.isEmpty()) {
            throw new IllegalStateException("No possible types available to set.");
        }
        if (this.possibleType.size() == 1) {
            this.type = this.possibleType.get(0);
        } else {
            this.type = this.possibleType.get((int) (Math.random() * this.possibleType.size()));
        }
        this.collapsed = true;
    }

    public void collapsed(ArrayList<Cell> neipo) {
        ArrayList<Integer> newPossibleTypes = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        for (Cell c : neipo) {
            int type = c.getType();
            ArrayList<Integer> neighborPossibleTypes = new ArrayList<>();
            if (Arrays.equals(c.getCellId(), this.LEFT)) {
                if (type == 0 || type == 1) {
                    neighborPossibleTypes.add(0);
                    neighborPossibleTypes.add(3);
                } else {
                    neighborPossibleTypes.addAll(Arrays.asList(1, 2, 4, 5));
                }
            } else if (Arrays.equals(c.getCellId(), this.DOWN)) {
                if (type == 0 || type == 2) {
                    neighborPossibleTypes.add(0);
                    neighborPossibleTypes.add(4);
                } else {
                    neighborPossibleTypes.addAll(Arrays.asList(1, 2, 3, 5));
                }
            } else if (Arrays.equals(c.getCellId(), this.RIGHT)) {
                if (type == 0 || type == 3) {
                    neighborPossibleTypes.add(0);
                    neighborPossibleTypes.add(1);
                } else {
                    neighborPossibleTypes.addAll(Arrays.asList(2, 3, 4, 5));
                }
            } else if (Arrays.equals(c.getCellId(), this.UP)) {
                if (type == 0 || type == 4) {
                    neighborPossibleTypes.add(0);
                    neighborPossibleTypes.add(2);
                } else {
                    neighborPossibleTypes.addAll(Arrays.asList(1, 3, 4, 5));
                }
            }
            newPossibleTypes.retainAll(neighborPossibleTypes);
        }
        this.possibleType = newPossibleTypes;
    }
}