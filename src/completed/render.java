package completed;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JPanel;

public class render extends JPanel {
    private ArrayList<Cell> cells;
    private Camera camera;

    public render(ArrayList<Cell> cells, Camera camera) {
        this.cells = cells;
        this.camera = camera;
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_Z:
                        camera.move(0, -10);
                        break;
                    case KeyEvent.VK_Q:
                        camera.move(-10, 0);
                        break;
                    case KeyEvent.VK_S:
                        camera.move(0, 10);
                        break;
                    case KeyEvent.VK_D:
                        camera.move(10, 0);
                        break;
                    case KeyEvent.VK_PLUS:
                    case KeyEvent.VK_EQUALS: // For the '+' key without shift
                    case KeyEvent.VK_ADD: // For the numpad '+' key
                        camera.zoom(1.1);
                        break;
                    case KeyEvent.VK_MINUS:
                    case KeyEvent.VK_SUBTRACT: // For the numpad '-' key
                        camera.zoom(0.9);
                        break;
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform originalTransform = g2d.getTransform();

        // Apply camera transformations
        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.scale(camera.getZoom(), camera.getZoom());
        g2d.translate(-camera.getX(), -camera.getY());

        for (Cell cell : cells) {
            Color color = new Color(cell.getColor().get(0), cell.getColor().get(1), cell.getColor().get(2));
            g2d.setColor(color);
            g2d.fillRect(cell.getX() * 10, cell.getY() * 10, 10, 10); // Assuming each pixel is 10x10
        }

        // Restore original transform
        g2d.setTransform(originalTransform);
    }

    public void updateCells(ArrayList<Cell> newCells) {
        this.cells = newCells;
        repaint();
    }
}