package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by benjaminclanet on 19/04/2015.
 */
public class Board extends JPanel implements IGameListener, KeyListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int SIZE_SQUARE = 20;
    private Game game;
    private Image snake;
    private Image snakeHead;
    private Image coin;
    private JLabel labelScore;
 
    public Board(int width, int height) {

        this.initImages();
        this.initGame(width, height);
        this.initLabelScore(width, height);
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(width, height));
    }

    private void initGame(int width, int height) {

        game = new Game(width, height);
        game.addGameListener(this);
    }

    private void initLabelScore(int width, int height) {

        labelScore = new JLabel("Score: 0");
        labelScore.setFont(new Font("TimesRoman", Font.BOLD, 30));
        labelScore.setForeground(Color.white);
        labelScore.setBackground(Color.CYAN);
        labelScore.setPreferredSize(new Dimension(200, 50));

        this.add(labelScore);
    }

    private void initImages() {

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/snake.jpg"));
        this.snake = icon.getImage();
        icon = new ImageIcon(getClass().getResource("/images/snake-head.jpg"));
        this.snakeHead = icon.getImage();
        icon = new ImageIcon(getClass().getResource("/images/coin.png"));
        this.coin = icon.getImage();
    }

    @Override
    public void refresh() {

        this.repaint();
    }

    private void draw(Graphics g) {

        ArrayList<Point> points = this.game.getSnake();

        for(int i = 0; i < points.size(); i++) {

            Point p = points.get(i);
            if(i == 0) {

                g.drawImage(this.snakeHead, p.x, p.y, SIZE_SQUARE, SIZE_SQUARE, this);
            }

            else {

                g.drawImage(this.snake, p.x, p.y, SIZE_SQUARE, SIZE_SQUARE, this);
            }
        }

        Point coin = this.game.getCoin();
        g.drawImage(this.coin, coin.x, coin.y, SIZE_SQUARE, SIZE_SQUARE, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

        this.game.keyChanged(String.valueOf(e.getKeyChar()));
    }

    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
