package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by benjaminclanet on 19/04/2015.
 */
public class Game implements ActionListener {

    enum GameKey {

        KeyUp,
        KeyDown,
        KeyRight,
        KeyLeft
    }

    private final int SIZE_SQUARE = 20;
    private GameKey currentKey;
    private IGameListener gameListener;
    private boolean isStarted;
    private ArrayList<Point> snakePosition;
    private Point coinPosition;
    private int width;
    private int height;
    private Timer timer;

    public Game(int width, int height) {

        this.width = width;
        this.height = height;
        this.isStarted = false;

        this.snakePosition = new ArrayList<>();
        this.snakePosition.add(new Point(width / 2, height / 2));
        this.snakePosition.add(new Point((width / 2), (height / 2) - SIZE_SQUARE));
        this.snakePosition.add(new Point((width / 2), (height / 2) - (SIZE_SQUARE * 2)));

        this.changeCoinPosition();
    }

    public void addGameListener(IGameListener listener) {

        this.gameListener = listener;
    }

    public void keyChanged(String name) {

        if(name.equals("z") && this.currentKey != GameKey.KeyDown) {

            this.currentKey = GameKey.KeyUp;
        }

        else if(name.equals("s") && this.currentKey != GameKey.KeyUp) {

            this.currentKey = GameKey.KeyDown;
        }

        else if(name.equals("q") && this.currentKey != GameKey.KeyRight) {

            this.currentKey = GameKey.KeyLeft;
        }

        else if(name.equals("d") && this.currentKey != GameKey.KeyLeft) {

            this.currentKey = GameKey.KeyRight;
        }

        if(!this.isStarted) {

            this.isStarted = true;
            this.timer = new Timer(80, this);
            this.timer.start();
        }
    }

    private void updateSnakePosition() {

        Point head = this.snakePosition.get(0);
        Point old = (Point) head.clone();
        this.updatePointFromDirection(head);

        for (int i = 1; i < this.snakePosition.size(); i++) {

            Point p = this.snakePosition.get(i);
            Point pClone = (Point)p.clone();
            p.x = old.x;
            p.y = old.y;
            old = pClone;
        }
    }

    private void enlargeSnake(Point newPoint) {

        this.snakePosition.add(0, newPoint);
    }

    private void changeCoinPosition() {

        int maxX = this.width / SIZE_SQUARE;
        int maxY = this.height / SIZE_SQUARE;
        int x = (int) (Math.random() * maxX - 1);
        int y = (int) (Math.random() * maxY - 1);

        if(this.coinPosition == null) {

            this.coinPosition = new Point();
        }

        this.coinPosition.x = x * SIZE_SQUARE;
        this.coinPosition.y = y * SIZE_SQUARE;
    }

    private void checkCoin() {

        Point head = this.snakePosition.get(0);
        Point check = (Point) head.clone();
        this.updatePointFromDirection(check);

        if(check.equals(this.coinPosition)) {

            this.changeCoinPosition();
            this.enlargeSnake(check);
        }
    }

    private boolean checkCollision() {

        Point head = this.snakePosition.get(0);
        Point check = (Point) head.clone();
        this.updatePointFromDirection(check);
        for(int i = 0; i < this.snakePosition.size(); i++) {

            Point p = this.snakePosition.get(i);
            if(p.equals(check)) {

                return false;
            }
        }

        if(check.x < 0 || check.x > this.width - SIZE_SQUARE || check.y < 0 || check.y > this.height - SIZE_SQUARE) {

            return false;
        }

        return true;
    }

    private void updatePointFromDirection(Point point) {

        switch (this.currentKey) {

            case KeyUp: {

                point.y -= SIZE_SQUARE;
                break;
            }

            case KeyDown: {

                point.y += SIZE_SQUARE;
                break;
            }

            case KeyLeft: {

                point.x -= SIZE_SQUARE;
                break;
            }

            case KeyRight: {

                point.x += SIZE_SQUARE;
                break;
            }
        }
    }

    public ArrayList<Point> getSnake() {

        return this.snakePosition;
    }

    public Point getCoin() {

        return this.coinPosition;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(this.checkCollision()) {

            this.checkCoin();
            this.updateSnakePosition();
            this.gameListener.refresh();
        }

        else {

            this.isStarted = false;
            this.timer.stop();
        }
    }
}
