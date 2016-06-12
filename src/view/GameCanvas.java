package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import model.Snake;
import model.SnakeBot;

public class GameCanvas extends JPanel {
	
	private String gameMessage;
	private final String PAUSE_MSG = "Game Paused :)";
	private final String ROCK_MSG = "You ate a rock :(";
	private final String WIN_MSG = "You Win!!!";
	private final String DEAD_MSG = "You ate your self:(";
	private final int BOARD_SIZE = 50;
	
	private int[][] board;
	private Point[] rockPoints;
	private Snake mySnake;
	private SnakeBot mySnakeHead;
	private List<Point> foodPoints;
	private List<Point> allPoints;
	private boolean isDead;
	
	public GameCanvas() {
		setOpaque(false);
		board = new int[BOARD_SIZE][BOARD_SIZE];
		allPoints = new ArrayList<>();
		loadAllPoints();
		
		int start = board.length/2;
		mySnake = new Snake(start,start,board.length);
		mySnakeHead = mySnake.getSnake();
		allPoints.remove(new Point(mySnakeHead.getX(), mySnakeHead.getY())); //remove our snake head from all points array.
		
		foodPoints = generateFoodPoints();
		rockPoints = createRockPoints();
	}
	
	private void loadAllPoints() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j =0; j < BOARD_SIZE; j++) {
				allPoints.add(new Point(i,j));
			}
		}
	}
	
	private Point[] createRockPoints() {
		Point[] points = new Point[20];
		
		for (int i = 0; i < points.length; i++) {
			points[i] = allPoints.get(new Random().nextInt(allPoints.size()));
		}
		
		return points;
	} 
	
	@Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        loadBoard();
        for (int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if (board[i][j] == 1) {
					 g2d.setColor(Color.BLACK);
					g2d.fill(new Ellipse2D.Double(i*10, j*10, 10, 10));
				} else if (board[i][j] == 2) {
					 g2d.setColor(Color.RED);
					g2d.fill(new Rectangle2D.Double(i*10, j*10, 10, 10));
				}
			}
		}
        g2d.setColor(Color.BLACK);
        if (isDead) {
        	
        	g2d.setFont(new Font("Ariel", Font.BOLD, 25));
        	g2d.drawString(gameMessage, (getWidth()/2)-60, getHeight()/2);
        }
	}
	
	/**
	 * Go through our board and set every spot with a snakebot to 1.
	 */
	private void loadBoard() {	
		SnakeBot curr = mySnakeHead;
		board = new int[BOARD_SIZE][BOARD_SIZE];  //clear old board.
		
		while (!curr.getNext().equals(mySnakeHead)) {
			int i = curr.getX();
			int j =  curr.getY();
			board[i][j] = 1;

			curr = curr.getNext();
		}
		int i = mySnakeHead.getX();
		int j =  mySnakeHead.getY();
		board[i][j] = 1; //points with snake body gets 1
		
		for (final Point p : foodPoints) {
			board[p.x][p.y] = 2; //points with food gets 2
		}
		System.out.println(allPoints.size());
	}
	
	private List<Point> generateFoodPoints() {
		final List<Point> foodPoints = new ArrayList<>();
		Random rand = new Random();
		for (int i=0; i < 35; i++) {
			Point p = new Point (allPoints.get(rand.nextInt(allPoints.size())));
			foodPoints.add(p);
			allPoints.remove(p);//remove rock points for all points array
		}
		return foodPoints;
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
	
	public boolean checkCollision(){
		for (int i = 0; i <foodPoints.size(); i++) {
			Point p = foodPoints.get(i);
			if (mySnakeHead.getX() == p.x && mySnakeHead.getY() == p.y) {
				foodPoints.remove(i);
				//increament score here
				return true;
			}
		}
		//System.out.println(foodPoints.size()+ ","+mySnake.getSize());
		return false;
	}
	
	/**
	 * Move the snake
	 * @param x the new x increament of the snake.
	 * @param y the new y incremant of the snake.
	 */
	public void moveSnake(int x, int y) {
		mySnake.move(x, y, board.length);
		if (checkPointInSnake(mySnakeHead.getX(),mySnakeHead.getY())){
			gameMessage = DEAD_MSG;
			isDead = true;
			firePropertyChange("eaten", null, null);
		} else if (checkCollision()) { //if the snake has eaten a fruit
			mySnake.grow(); //grow the snake.
			if (foodPoints.isEmpty()){ //if that was the last food.
				gameMessage = WIN_MSG;
				isDead = true;
				firePropertyChange("win", null, null);
			}
		}
	}
}
