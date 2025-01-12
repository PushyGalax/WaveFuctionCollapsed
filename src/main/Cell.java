package main;



//Run thread until not collapsed, if collapsed free from running cell list on the environnement and just preserved the image on the environnement
//make a function that return state and possibility for other
//on the thread compute all the rest
//if neighborg not in the cell in action list create it

import java.util.ArrayList;
import java.util.HashMap;

public class Cell extends Thread{
    // VARIABLES
    private int x;
    private int y;
    private Environnement environnement;
    private final int[] UP;
    private final int[] RIGHT;
    private final int[] DOWN;
    private final int[] LEFT;

    private int [] id;

    private boolean collapsed;

    private int probabilityOfType;

    private ArrayList<int[]> neighborg;

    // make a structure to store the probability of each type of cell
    private ArrayList<HashMap> possibility;


    // CONSTRUCTOR

    public Cell(int x, int y, Environnement environnement) {
        this.x = x;
        this.y = y;
        this.environnement = environnement;

        this.neighborg = new ArrayList<>();
        this.id = new int[2];
        this.UP = new int[2];
        this.RIGHT = new int[2];
        this.DOWN = new int[2];
        this.LEFT = new int[2];
        this.possibility = new ArrayList<>();

        this.id[0] = this.x;
        this.id[1] = this.y;

        this.initializeNeighbors();
    }

    private void makePossibility() {
        //BLANK 0
        HashMap<Integer,ArrayList> type = new HashMap<>();
        ArrayList<Integer[]> connection = new ArrayList<>();
        int i = 0;
        connection.add(new Integer[]{0,0,0,0});
        type.put(i, connection);
        this.possibility.add(type);

        //piece 1
        i = i++;
        type = new HashMap<>();
        connection = new ArrayList<>();
        connection.add(new Integer[]{1,1,0,1});
        type.put(i, connection);
        this.possibility.add(type);

        //piece 2
        i = i++;
        type = new HashMap<>();
        connection = new ArrayList<>();
        connection.add(new Integer[]{1,1,1,0});
        type.put(i, connection);
        this.possibility.add(type);

        //piece 3
        i = i++;
        type = new HashMap<>();
        connection = new ArrayList<>();
        connection.add(new Integer[]{0,1,1,1});
        type.put(i, connection);
        this.possibility.add(type);

        //piece 4
        i = i++;
        type = new HashMap<>();
        connection = new ArrayList<>();
        connection.add(new Integer[]{1,0,1,1});
        type.put(i, connection);
        this.possibility.add(type);
    }

    // METHODS
    private void initializeNeighbors() {
        this.UP[0] = this.x-1;
        this.UP[1] = this.y;

        this.RIGHT[0] = this.x;
        this.RIGHT[1] = this.y+1;

        this.DOWN[0] = this.x+1;
        this.DOWN[1] = this.y;

        this.LEFT[0] = this.x;
        this.LEFT[1] = this.y-1;

        this.neighborg.add(this.UP);
        this.neighborg.add(this.RIGHT);
        this.neighborg.add(this.DOWN);
        this.neighborg.add(this.LEFT);
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

//    private void isKillable() {
//        int cpt = 0;
//        for (int[] pos : this.neighborg) {
//            if (environnement.isCellCollapsed(pos)) cpt+=1;
//        }
//        if (cpt == 4) environnement.killCell(this);
//    }


    // FUNCTION COLLAPSE


    // THREAD
    public void run() {

    }
}
