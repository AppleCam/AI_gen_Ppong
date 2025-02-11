import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame extends JFrame implements ActionListener, KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Timer timer;
    private Paddle player1, player2;
    private Ball ball;
    private int player1Score = 0, player2Score = 0;

    public PongGame() {
        setTitle("Pong Game");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        timer = new Timer(5, this);
        ball = new Ball();
        player1 = new Paddle(10, HEIGHT / 2 - 30);
        player2 = new Paddle(WIDTH - 30, HEIGHT / 2 - 30);

        addKeyListener(this);
        setFocusable(true);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        player1.draw(g);
        player2.draw(g);
        ball.draw(g);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Player 1: " + player1Score, 50, 50);
        g.drawString("Player 2: " + player2Score, WIDTH - 200, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ball.move();
        ball.checkCollision(player1, player2);
        if (ball.getX() < 0) {
            player2Score++;
            ball.reset();
        } else if (ball.getX() > WIDTH) {
            player1Score++;
            ball.reset();
        }
        player1.move();
        player2.move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player1.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            player1.moveDown();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player2.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player2.moveDown();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
            player1.stop();
        } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            player2.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No implementation needed
    }

    public static void main(String[] args) {
        new PongGame();
    }

    class Paddle {
        private int x, y, ySpeed = 0;
        private static final int WIDTH = 20, HEIGHT = 60;

        public Paddle(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics g) {
            g.fillRect(x, y, WIDTH, HEIGHT);
        }

        public void moveUp() {
            ySpeed = -5;
        }

        public void moveDown() {
            ySpeed = 5;
        }

        public void stop() {
            ySpeed = 0;
        }

        public void move() {
            y += ySpeed;
            if (y < 0) y = 0;
            if (y > PongGame.HEIGHT - HEIGHT) y = PongGame.HEIGHT - HEIGHT;
        }

        public Rectangle getBounds() {
            return new Rectangle(x, y, WIDTH, HEIGHT);
        }
    }

    class Ball {
        private int x, y, xSpeed = 5, ySpeed = 5;
        private static final int SIZE = 20;

        public Ball() {
            reset();
        }

        public void draw(Graphics g) {
            g.fillOval(x, y, SIZE, SIZE);
        }

        public void move() {
            x += xSpeed;
            y += ySpeed;
            if (y < 0 || y > PongGame.HEIGHT - SIZE) ySpeed = -ySpeed;
        }

        public void checkCollision(Paddle p1, Paddle p2) {
            if (getBounds().intersects(p1.getBounds()) || getBounds().intersects(p2.getBounds())) {
                xSpeed = -xSpeed;
            }
        }

        public void reset() {
            x = PongGame.WIDTH / 2 - SIZE / 2;
            y = PongGame.HEIGHT / 2 - SIZE / 2;
        }

        public int getX() {
            return x;
        }

        public Rectangle getBounds() {
            return new Rectangle(x, y, SIZE, SIZE);
        }
    }
}

