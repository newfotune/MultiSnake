package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.UIManager;

public class SnakeGui extends JFrame implements PropertyChangeListener {
	private GameCanvas canvas;
	private Timer myTimer;
	private int currentDirection;

	public SnakeGui() {
		// super("TranslucentWindow");
		//setUndecorated(true);
		this.setAlwaysOnTop(true);
		setLocation(200, 200);

	//	setBackground(new Color(0, 0, 0, 0));
	//	setOpacity(0.1f);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currentDirection = 2;
		canvas = new GameCanvas();
		canvas.addKeyListener(new MoveListener());
		canvas.addPropertyChangeListener(this);
		canvas.setFocusable(true);
		canvas.requestFocusInWindow();

		setContentPane(canvas);
		myTimer = new Timer(500, new TimerListener());

		setSize(new Dimension(500, 500));
		setVisible(true);
		myTimer.start();
	}
	private void closeProgram() {
		this.dispose();
	}
	// direction Number = 3
	private void moveLeft() {
		canvas.moveSnake(-1, 0);
		canvas.repaint();
	}
	// direction Number = 1
	private void moveRight() {
		canvas.moveSnake(1, 0);
		canvas.repaint();
	}
	// direction Number = 0
	private void moveUp() {
		canvas.moveSnake(0, -1);
		canvas.repaint();
	}
	// direction Number = 2
	private void moveDown() {
		canvas.moveSnake(0, 1);
		canvas.repaint();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals("eaten")) {
			myTimer.stop();
		}
	}

	private class MoveListener extends KeyAdapter {

		protected MoveListener() {
			super();
			// do nothing
		}

		@Override
		public void keyPressed(final KeyEvent theEvent) {
			int key = theEvent.getKeyCode();
			switch (key) {
				case KeyEvent.VK_LEFT :
					currentDirection = 3;
					if (myTimer.isRunning())
						moveLeft();
					break;
				case KeyEvent.VK_RIGHT :
					currentDirection = 1;
					if (myTimer.isRunning())
						moveRight();
					break;
				case KeyEvent.VK_UP :
					currentDirection = 0;
					if (myTimer.isRunning())
						moveUp();
					break;
				case KeyEvent.VK_DOWN :
					currentDirection = 2;
					if (myTimer.isRunning())
						moveDown();
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent evt) {
		}

		@Override
		public void keyTyped(KeyEvent evt) {
		}
	}

	/**
	 * An Action Listener for the timer.
	 * 
	 * @author n4tunec@uw.edu.
	 * @version winter 2015.
	 */
	private class TimerListener implements ActionListener {
		/** Default constructor for the listener. */
		protected TimerListener() {
			// do nothing.
		}

		/**
		 * This moves the object with the timer.
		 * 
		 * @param theEvent
		 *            this is the event fired.
		 */
		@Override
		public void actionPerformed(final ActionEvent theEvent) {
			// canvas.printSnake();
			switch (currentDirection) {
				case 0 :
					moveUp();
					break;
				case 1 :
					moveRight();
					break;
				case 2 :
					moveDown();
					break;
				case 3 :
					moveLeft();
					break;
			}

			canvas.repaint();
		}
	}
}
