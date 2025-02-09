package completed;

import java.util.ArrayList;
import java.util.Map;

public class Tile {
    /**
     * Tile is a type that contains the pixel for a tile
     */

    // VARIABLES
    private ArrayList<ArrayList> tile;
    private int occurence;
    private int id;
    private ArrayList<Integer> possibilityUp;
    private ArrayList<Integer> possibilityDown;
    private ArrayList<Integer> possibilityLeft;
    private ArrayList<Integer> possibilityRight;


    // CONSTRUCTORS
    public Tile(ArrayList<Integer> pix1, ArrayList<Integer> pix2, ArrayList<Integer> pix3, ArrayList<Integer> pix4, ArrayList<Integer> pix5, ArrayList<Integer> pix6, ArrayList<Integer> pix7, ArrayList<Integer> pix8, ArrayList<Integer> pix9) {
        tile = new ArrayList<ArrayList>();
        ArrayList<ArrayList<Integer>> row1 = new ArrayList<>();
        row1.add(pix1);
        row1.add(pix2);
        row1.add(pix3);

        ArrayList<ArrayList<Integer>> row2 = new ArrayList<>();
        row2.add(pix4);
        row2.add(pix5);
        row2.add(pix6);

        ArrayList<ArrayList<Integer>> row3 = new ArrayList<>();
        row3.add(pix7);
        row3.add(pix8);
        row3.add(pix9);

        tile.add(row1);
        tile.add(row2);
        tile.add(row3);

        this.occurence = 0;
        this.possibilityUp = new ArrayList<>();
        this.possibilityDown = new ArrayList<>();
        this.possibilityLeft = new ArrayList<>();
        this.possibilityRight = new ArrayList<>();
    }

    // METHODS
    public ArrayList<ArrayList> getTile() {
        return tile;
    }

    public String toString() {
        String str = "";
        for (ArrayList<ArrayList<Integer>> row : tile) {
            for (ArrayList<Integer> pix : row) {
                str += "(";
                str += pix.get(0) + ", ";
                str += pix.get(1) + ", ";
                str += pix.get(2) + ") ";
            }
            str += "\n";
        }
        return str;
    }

    // compare if this is equal to another tile
    public boolean equals(Tile other) {
        return this.toString().equals(other.toString());
    }

    public int getOccurence() {
        return occurence;
    }

    public void increaseOccurence() {
        this.occurence++;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }

    public void addPossibilityUp(int id) {
        this.possibilityUp.add(id);
    }

    public void addPossibilityDown(int id) {
        this.possibilityDown.add(id);
    }

    public void addPossibilityLeft(int id) {
        this.possibilityLeft.add(id);
    }

    public void addPossibilityRight(int id) {
        this.possibilityRight.add(id);
    }

    public ArrayList<Integer> getPossibilityUp() {
        return this.possibilityUp;
    }

    public ArrayList<Integer> getPossibilityDown() {
        return this.possibilityDown;
    }

    public ArrayList<Integer> getPossibilityLeft() {
        return this.possibilityLeft;
    }

    public ArrayList<Integer> getPossibilityRight() {
        return this.possibilityRight;
    }

    public ArrayList<ArrayList> getLine(int i) {
        return tile.get(i);
    }

    public ArrayList<ArrayList<Integer>> getRow(int i) {
        ArrayList<ArrayList<Integer>> row = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            row.add((ArrayList<Integer>) tile.get(j).get(i));
        }
        return row;
    }

    public boolean isOverlappingUp(Tile other) {
        boolean result = false;
        if (this.getLine(1).equals(other.getLine(0)) && this.getLine(2).equals(other.getLine(1))) {
            result = true;
        }
        return result;
    }

    public boolean isOverlappingDown(Tile other) {
        boolean result = false;
        if (this.getLine(0).equals(other.getLine(1)) && this.getLine(1).equals(other.getLine(2))) {
            result = true;
        }
        return result;
    }

    public boolean isOverlappingLeft(Tile other) {
        boolean result = false;
        if (this.getRow(1).equals(other.getRow(0)) && this.getRow(2).equals(other.getRow(1))) {
            result = true;
        }
        return result;
    }

    public boolean isOverlappingRight(Tile other) {
        boolean result = false;
        if (this.getRow(0).equals(other.getRow(1)) && this.getRow(1).equals(other.getRow(2))) {
            result = true;
        }
        return result;
    }

    public ArrayList<Integer> getColor() {
        return (ArrayList<Integer>) tile.get(1).get(1);
    }
}
