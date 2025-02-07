package main;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.Map;

public class renderEnvironnement {
    private Frame frame;
    private Canvas canvas;
    private Image[] images = new Image[6];
    private int tileSize = 5;
    private int cameraX = 0;
    private int cameraY = 0;
    private Map<Point, Integer> imagePositions = new HashMap<>();
    private BufferStrategy bufferStrategy;

    public void render() {
        frame = new Frame("Environment Rendering");
        frame.setSize(800, 600);
        frame.setBackground(Color.BLUE);
        frame.setLayout(new BorderLayout());

        canvas = new Canvas();
        frame.add(canvas, BorderLayout.CENTER);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> cameraY -= 10;
                    case KeyEvent.VK_DOWN -> cameraY += 10;
                    case KeyEvent.VK_LEFT -> cameraX -= 10;
                    case KeyEvent.VK_RIGHT -> cameraX += 10;
                    case KeyEvent.VK_PLUS, KeyEvent.VK_ADD -> tileSize += 1; // Zoom in
                    case KeyEvent.VK_MINUS, KeyEvent.VK_SUBTRACT -> tileSize = Math.max(1, tileSize - 1); // Zoom out
                }
                drawAllImages();
            }
        });

        loadImages();
        drawAllImages();
    }

    private void loadImages() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        for (int i = 0; i < images.length; i++) {
            images[i] = toolkit.getImage("res\\img\\" + i + ".png");
        }
    }

    public void setImagesPosition(int x, int y, int type) {
        if (type < 0 || type >= images.length || images[type] == null) {
            return;
        }
        imagePositions.put(new Point(x, y), type);
        drawAllImages();
    }

    private void drawAllImages() {
        do {
            do {
                Graphics g = bufferStrategy.getDrawGraphics();
                clearBuffer(g);
                for (Map.Entry<Point, Integer> entry : imagePositions.entrySet()) {
                    Point position = entry.getKey();
                    int type = entry.getValue();
                    int drawX = (position.x - cameraX) * tileSize;
                    int drawY = (position.y - cameraY) * tileSize;
                    if (type >= 0 && type < images.length && images[type] != null) {
                        g.drawImage(images[type], drawX, drawY, images[type].getWidth(canvas) * tileSize, images[type].getHeight(canvas) * tileSize, canvas);
                    }
                }
                g.dispose();
            } while (bufferStrategy.contentsRestored());
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }

    private void clearBuffer(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}