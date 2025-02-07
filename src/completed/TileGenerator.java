package completed;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileGenerator {
    /*
    * This class is responsible for generating the tiles that will be used in the game.
    * The tiles are generated by creating a 3x3 grid of pixels, where each pixel is represented by an array of 3 integers.
    * This is created by reading the pixel values from an image file.
     */

    //VARIABLES
    private String path;
    private Image img;
    private int width;
    private int height;
    private int imgWidth;
    private int imgHeight;
    private ArrayList<String> StringRGB = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<Integer>>> pixelMatrix = new ArrayList<>();
    private ArrayList<Tile> tiles = new ArrayList<>();

    //CONSTRUCTORS
    public TileGenerator(String path, int width, int height) {
        this.path = path;
        this.width = width;
        this.height = height;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.img = toolkit.getImage(path);

        MediaTracker tracker = new MediaTracker(new Component() {});
        tracker.addImage(img, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Load the image
        try {
            StringRGB = this.loadImages();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(StringRGB);
        pixelMatrix = this.StringtoRGB(StringRGB);

        //System.out.println(this.imgWidth);

        generateTile();
        clearTiles();
        makeId();
        makePossibility();
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public boolean getTest() {
        return test();
    }

    private boolean test() {
        boolean noDuplicates = true;
        boolean allHaveId = true;
        boolean allPossibilitiesFinished = true;

        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.getId() == -1) {
                allHaveId = false;
            }
            if (tile.getPossibilityUp().isEmpty() || tile.getPossibilityDown().isEmpty() || tile.getPossibilityLeft().isEmpty() || tile.getPossibilityRight().isEmpty()) {
                allPossibilitiesFinished = false;
            }
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tiles.get(i).equals(tiles.get(j))) {
                    noDuplicates = false;
                }
            }
        }

        return noDuplicates && allHaveId && allPossibilitiesFinished;
    }


    //METHODS
    public ArrayList<String> loadImages() {
        ArrayList<String> rgbValues = new ArrayList<>();
        try {
            int width = img.getWidth(null);
            int height = img.getHeight(null);
            this.imgWidth = width;
            this.imgHeight = height;
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bufferedImage.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = bufferedImage.getRGB(x, y);
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = pixel & 0xff;
                    rgbValues.add(red + "," + green + "," + blue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rgbValues;
    }

    private ArrayList<ArrayList<ArrayList<Integer>>> StringtoRGB(ArrayList<String> rgbValues) {
        ArrayList<ArrayList<ArrayList<Integer>>> tiles = new ArrayList<>();
        int line = 0;
        ArrayList<ArrayList<Integer>> tile = new ArrayList<>();
        for (String pix : rgbValues) {
            ArrayList<Integer> color = new ArrayList<>();
            int i = 0;
            for (String code : pix.split(",")) {
                color.add(Integer.parseInt(code));
                i++;
            }
            tile.add(color);
            line++;
            if (line == this.imgWidth) {
                tiles.add(tile);
                tile = new ArrayList<>();
                line = 0;
            }
        }
        return tiles;
    }

    private void generateTile() {
        for (int line = 0; line<this.imgHeight;line++) {
            for (int row = 0; row<this.imgWidth; row++) {
                Tile tile;
                ArrayList<Integer> colorPix1 = pixelMatrix.get(line).get(row);
                ArrayList<Integer> colorPix2 = pixelMatrix.get(line).get((row+1)%this.imgWidth);
                ArrayList<Integer> colorPix3 = pixelMatrix.get(line).get((row+2)%this.imgWidth);

                ArrayList<Integer> colorPix4 = pixelMatrix.get((line+1)%this.imgHeight).get(row);
                ArrayList<Integer> colorPix5 = pixelMatrix.get((line+1)%this.imgHeight).get((row+1)%this.imgWidth);
                ArrayList<Integer> colorPix6 = pixelMatrix.get((line+1)%this.imgHeight).get((row+2)%this.imgWidth);

                ArrayList<Integer> colorPix7 = pixelMatrix.get((line+2)%this.imgHeight).get(row);
                ArrayList<Integer> colorPix8 = pixelMatrix.get((line+2)%this.imgHeight).get((row+1)%this.imgWidth);
                ArrayList<Integer> colorPix9 = pixelMatrix.get((line+2)%this.imgHeight).get((row+2)%this.imgWidth);

                tile = new Tile(colorPix1, colorPix2, colorPix3, colorPix4, colorPix5, colorPix6, colorPix7, colorPix8 ,colorPix9);
                tiles.add(tile);
            }
        }
    }

    private void clearTiles() {
        ArrayList<Tile> tilesClear = new ArrayList<>();
        for (int i = 0; i < tiles.size(); i++) {
            Tile test = tiles.get(i);
            boolean skip = false;
            for (int j = 0; j<tilesClear.size(); j++) {
                if (test.equals(tilesClear.get(j))) {
                    skip=true;
                    break;
                }
            }
            if (!skip) {
                for (int j = i; j < tiles.size(); j++) {
                    if (test.equals(tiles.get(j))) test.increaseOccurence();
                }
                tilesClear.add(test);
            }
        }
        tiles = tilesClear;
    }

    private void makeId() {
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).setId(i);
        }
    }

    private boolean isOverlappingUp(Tile tile1, Tile tile2) {
        boolean result = false;
        if (tile1.getLine(1).equals(tile2.getLine(0)) && tile1.getLine(2).equals(tile2.getLine(1))) {
            result = true;
        }
        return result;
    }

    private boolean isOverlappingDown(Tile tile1, Tile tile2) {
        boolean result = false;
        if (tile1.getLine(0).equals(tile2.getLine(1)) && tile1.getLine(1).equals(tile2.getLine(2))) {
            result = true;
        }
        return result;
    }

    private boolean isOverlappingLeft(Tile tile1, Tile tile2) {
        boolean result = false;
        if (tile1.getRow(1).equals(tile2.getRow(0)) && tile1.getRow(2).equals(tile2.getRow(1))) {
            result = true;
        }
        return result;
    }

    private boolean isOverlappingRight(Tile tile1, Tile tile2) {
        boolean result = false;
        if (tile1.getRow(0).equals(tile2.getRow(1)) && tile1.getRow(1).equals(tile2.getRow(2))) {
            result = true;
        }
        return result;
    }


    private void makePossibility() {
        for (int i = 0; i < tiles.size(); i++) {
            Tile inWork = tiles.get(i);
            for (int j = 0; j < tiles.size(); j++) {
                Tile check = tiles.get(j);
                //UP
                if (isOverlappingUp(inWork, check)) {
                    inWork.addPossibilityUp(check.getId());
                }
                //DOWN
                if (isOverlappingDown(inWork, check)) {
                    inWork.addPossibilityDown(check.getId());
                }
                //LEFT
                if (isOverlappingLeft(inWork, check)) {
                    inWork.addPossibilityLeft(check.getId());
                }
                //RIGHT
                if (isOverlappingRight(inWork, check)) {
                    inWork.addPossibilityRight(check.getId());
                }
            }
        }
    }
}
