package completed;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        int[] pix1 = {0, 0, 0};
//        int[] pix2 = {1, 1, 1};
//        int[] pix3 = {2, 2, 2};
//        int[] pix4 = {3, 3, 3};
//        int[] pix5 = {4, 4, 4};
//        int[] pix6 = {5, 5, 5};
//        int[] pix7 = {6, 6, 6};
//        int[] pix8 = {7, 7, 7};
//        int[] pix9 = {8, 8, 8};
//
//        Tile test = new Tile(pix1, pix2, pix3, pix4, pix5, pix6, pix7, pix8, pix9);
//
//        System.out.println(test);

        TileGenerator tg = new TileGenerator("res/samples/City.png", 3, 3);
        tg.loadImages();
        ArrayList<Tile> tiles = tg.getTiles();

        Environment env = new Environment(600, 600, tiles);
    }
}
