package completed;

import java.util.ArrayList;

public class Tile {
    /**
     * Tile is a type that contains the pixel for a tile
     */

    // VARIABLES
    private ArrayList<ArrayList> tile;
    private int occurence;


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
}
