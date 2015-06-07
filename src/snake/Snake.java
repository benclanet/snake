package snake;

import javax.swing.*;
import java.awt.*;

/**
 * Created by benjaminclanet on 19/04/2015.
 */
public class Snake extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Snake() {

        initUI();
    }

    private void initUI() {

        int width = 400;
        int height = 400;
        this.setTitle("Snake");
        this.setSize(width, height);
        this.setBackground(Color.BLUE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Board board = new Board(width, height);
        this.addKeyListener(board);
        this.add(board);
        this.pack();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Snake ex = new Snake();
            ex.setVisible(true);
        });
    }
}
