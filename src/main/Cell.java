package main;



//Run thread until not collapsed, if collapsed free from running cell list on the environnement and just preserved the image on the environnement
//make a function that return state and possibility for other
//on the thread compute all the rest
//if neighborg not in the cell in action list create it

import java.util.ArrayList;

public class Cell extends Thread{
    private int x;
    private int y;
    private Environnement environnement;
    int[] up;
    int[] right;
    int[] down;
    int[] left;

    private int [] id;

    private boolean collapsed;

    private int probabilityOfType;

    private ArrayList<int[]> neighborg;

    public Cell(int x, int y, Environnement environnement) {
        this.x = x;
        this.y = y;
        this.environnement = environnement;

        this.neighborg = new ArrayList<>();
        this.id = new int[2];
        this.up = new int[2];
        this.right = new int[2];
        this.down = new int[2];
        this.left = new int[2];

        this.id[0] = this.x;
        this.id[1] = this.y;

        this.initializeNeighbors();
    }

    private void initializeNeighbors() {
        this.up[0] = this.x-1;
        this.up[1] = this.y;

        this.right[0] = this.x;
        this.right[1] = this.y+1;

        this.down[0] = this.x+1;
        this.down[1] = this.y;

        this.left[0] = this.x;
        this.left[1] = this.y-1;

        this.neighborg.add(this.up);
        this.neighborg.add(this.right);
        this.neighborg.add(this.down);
        this.neighborg.add(this.left);
    }

    public boolean isCollapsed() {return this.collapsed;}

    public int[] getCellId() {return this.id;}

    public int[] getPosition() {
        int[] toReturn = new int[2];
        toReturn[0] = this.x;
        toReturn[1] = this.y;
        return toReturn;
    }

    private void update() {

    }

    public int getProbabilityOfType() {
        return probabilityOfType;
    }

    private void isKillable() {
        int cpt = 0;
        for (int[] pos : this.neighborg) {
            if (environnement.isCellCollapsed(pos)) cpt+=1;
        }
        if (cpt == 4) environnement.killCell(this);
    }

    public void run() {

    }
}
