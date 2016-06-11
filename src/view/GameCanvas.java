package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import model.Snake;
import model.SnakeBot;

public class GameCanvas extends JPanel {
	
	private int[][] board;
	private Snake mySnake;
	private SnakeBot mySnakeHead;
	
	public GameCanvas() {
		setOpaque(false);
		board = new int[50][50];

		int start = board.length/2;
		mySnake = new Snake(start,start,board.length);
		mySnakeHead = mySnake.getSnake();
	}
	
	@Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.85f));
        loadBoard();
        for (int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if (board[i][j] == 1) {
					g2d.fill(new Ellipse2D.Double(i*10, j*10, 10, 10));
				}
			}
		}
	}
	
	/**
	 * Go through our board and set every spot with a snakebot to 1.
	 */
	private void loadBoard() {
		SnakeBot curr = mySnakeHead;
		board = new int[50][50];  //clear old board.
		int length = board.length;
		while (!curr.getNext().equals(mySnakeHead)) {
			int i = Math.abs(curr.getX())%length;
			int j = Math.abs(curr.getY())%length;
			
			board[i][j] = 1;
			curr = curr.getNext();
		}
		int i = Math.abs(curr.getX())%length;
		int j = Math.abs(curr.getY())%length;
		
		board[i][j] = 1;
	}
	/**
	 * Print the snake to the console.
	 */
	public void printSnake() {
		loadBoard(); //load board with data from bots.
		for (int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if (board[i][j] == 1) {
					System.out.print('x');
				} else {
					System.out.print('O');
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	/**
	 * Checks to see if any part of the snake has this x and Y location
	 * @param x the x being searched for.
	 * @param y the Y being searched for.
	 * @return true is the snake body contains the coordinates and false otherwise.
	 */
	private boolean checkPointInSnake(int x, int y) {
		SnakeBot curr = mySnakeHead.getNext();
		boolean isExist = false;
		while (curr.getNext()!= mySnakeHead){
			if(curr.getNext().getX() == x && curr.getNext().getY() == y){
				isExist = true;
				break;
			}
			curr = curr.getNext();
		}
		return isExist;
	}
	
	/**
	 * Move the snake
	 * @param x the new x increament of the snake.
	 * @param y the new y incremant of the snake.
	 */
	public void moveSnake(int x, int y) {
		mySnake.move(x, y);
		if (checkPointInSnake(mySnakeHead.getX(),mySnakeHead.getY())){
			firePropertyChange("eaten", null, null);
		}
	}
}
