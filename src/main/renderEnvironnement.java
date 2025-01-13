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
    private final Map<Point, Integer> imagePositions = new HashMap<>();
    private BufferStrategy bufferStrategy;

    public void render() {
        frame = new Frame("Environment Rendering");
        frame.setSize(800, 600);
        frame.setBackground(Color.BLACK);
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
            images[i] = toolkit.getImage("C:\\Users\\galax\\Desktop\\java\\WaveFunctionCollapsed\\res\\img\\" + i + ".png");

            // Pre-load image to avoid delays during rendering
            MediaTracker tracker = new MediaTracker(canvas);
            tracker.addImage(images[i], 0);
            try {
                tracker.waitForAll();
            } catch (InterruptedException e) {
                System.out.println("Image loading interrupted: " + e.getMessage());
            }
        }
    }

    public void setImagesPosition(int x, int y, int type) {
        if (type < 0 || type >= images.length || images[type] == null) {
            return;
        }
        synchronized (imagePositions) {
            imagePositions.put(new Point(x, y), type);
        }
        drawAllImages();
    }

    private long lastRenderTime = 0;

    private void clearBuffer(Graphics g) {
        g.setColor(Color.BLACK); // Définit la couleur de fond (noir dans ce cas)
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Remplit tout le canvas
    }

    private Map<Integer, Image[]> scaledImageCache = new HashMap<>();

    private Image[] getScaledImages() {
        // Si des images mises en cache existent déjà pour cette taille de tuile, on les retourne
        if (scaledImageCache.containsKey(tileSize)) {
            return scaledImageCache.get(tileSize);
        }

        // Sinon, on redimensionne les images et les met en cache
        Image[] scaledImages = new Image[images.length];
        for (int i = 0; i < images.length; i++) {
            if (images[i] != null) {
                scaledImages[i] = images[i].getScaledInstance(
                        images[i].getWidth(canvas) * tileSize, // Nouvelle largeur
                        images[i].getHeight(canvas) * tileSize, // Nouvelle hauteur
                        Image.SCALE_SMOOTH // Utilise un algorithme de redimensionnement de haute qualité
                );
            }
        }
        scaledImageCache.put(tileSize, scaledImages); // Mise en cache des images redimensionnées
        return scaledImages;
    }

        private void drawAllImages() {
        long currentTime = System.nanoTime();
        if ((currentTime - lastRenderTime) < 16_666_667) { // 16ms for ~60FPS
            return;
        }
        lastRenderTime = currentTime;

        do {
            do {
                Graphics g = bufferStrategy.getDrawGraphics();
                clearBuffer(g);

                Map<Point, Integer> positionsCopy;
                synchronized (imagePositions) {
                    positionsCopy = new HashMap<>(imagePositions);
                }
                Image[] scaledImages = getScaledImages();

                for (Map.Entry<Point, Integer> entry : positionsCopy.entrySet()) {
                    Point position = entry.getKey();
                    int type = entry.getValue();
                    int drawX = (position.x - cameraX) * tileSize;
                    int drawY = (position.y - cameraY) * tileSize;
                    if (type >= 0 && type < scaledImages.length && scaledImages[type] != null) {
                        g.drawImage(scaledImages[type], drawX, drawY, canvas);
                    }
                }

                g.dispose();
            } while (bufferStrategy.contentsRestored());
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }
}