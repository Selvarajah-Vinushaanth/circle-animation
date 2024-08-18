import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CircleAnimation extends JFrame {
    private static final int NUM_CIRCLES = 50;
    private static final int MAX_RADIUS = 30;
    private static final int MAX_SPEED = 3;

    private List<Circle> circles;
    private Random random;

    public CircleAnimation() {
        setTitle("Circle Animation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setVisible(true); // Make the frame visible before initializing circles

        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to full screen

        circles = new ArrayList<>();
        random = new Random();

        for (int i = 0; i < NUM_CIRCLES; i++) {
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight());
            int radius = random.nextInt(MAX_RADIUS) + 10;
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            int dx = random.nextInt(2 * MAX_SPEED + 1) - MAX_SPEED;
            int dy = random.nextInt(2 * MAX_SPEED + 1) - MAX_SPEED;
            circles.add(new Circle(x, y, radius, color, dx, dy));
        }

        new AnimationThread().start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Circle circle : circles) {
            g2d.setColor(circle.getColor());
            g2d.fillOval(circle.getX() - circle.getRadius(), circle.getY() - circle.getRadius(),
                    2 * circle.getRadius(), 2 * circle.getRadius());
        }
    }

    private class Circle {
        private int x;
        private int y;
        private int radius;
        private Color color;
        private int dx;
        private int dy;

        public Circle(int x, int y, int radius, Color color, int dx, int dy) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
            this.dx = dx;
            this.dy = dy;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getRadius() {
            return radius;
        }

        public Color getColor() {
            return color;
        }

        public void move() {
            x += dx;
            y += dy;
            if (x - radius < 0 || x + radius > getWidth()) {
                dx = -dx;
            }
            if (y - radius < 0 || y + radius > getHeight()) {
                dy = -dy;
            }
        }
    }

    private class AnimationThread extends Thread {
        @Override
        public void run() {
            while (true) {
                for (Circle circle : circles) {
                    circle.move();
                }
                repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CircleAnimation().setVisible(true));
    }
}
