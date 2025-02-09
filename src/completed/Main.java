package completed;

import javax.swing.JFrame;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        TileGenerator tg = new TileGenerator("res/samples/Skyline.png", 3, 3);
        tg.loadImages();
        ArrayList<Tile> tiles = tg.getTiles();

        Environment env = new Environment(30, 30, tiles); // Adjusted size for better visualization
        Camera camera = new Camera(60, 60, 1.0); // Initial camera position and zoom

        JFrame frame = new JFrame();
        render renderPanel = new render(env.getCells(), camera);
        frame.add(renderPanel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            env.update();
            renderPanel.repaint();
            System.out.println("Updating & Repainting");
        }
    }
}