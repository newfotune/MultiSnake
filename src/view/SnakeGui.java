package view;

import java.awt.BorderLayout;
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
	private ControlsPopup controls;

	public SnakeGui() {
		setUndecorated(true);
		this.setAlwaysOnTop(true);
		setLocation(600, 100);
		setBackground(new Color(0, 255, 0, 50));
		
	//	setOpacity(0.1f);
		controls = new ControlsPopup();
		controls.addPropertyChangeListener(this);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currentDirection = new Random().nextInt(4);
		canvas = new GameCanvas();
		canvas.addKeyListener(new MoveListener());
		canvas.addPropertyChangeListener(this);
		canvas.setFocusable(true);
		canvas.requestFocusInWindow();

		setContentPane(canvas);
		getContentPane().setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		
		myTimer = new Timer(500, new TimerListener());

		setSize(new Dimension(500, 500));
		setVisible(true);
		myTimer.start();
	}
	private void closeProgram() {
		myTimer.stop();
		dispose();
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
		} else if (event.getPropertyName().equals("win")) {
			myTimer.stop();
		} else if (event.getPropertyName().equals("close")) {
			closeProgram();
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
					if (myTimer.isRunning() && currentDirection != 1) {
						currentDirection = 3;
						moveLeft();
					}
					break;
				case KeyEvent.VK_RIGHT :
					if (myTimer.isRunning() && currentDirection != 3){
						currentDirection = 1;
						moveRight();
					}	
					break;
				case KeyEvent.VK_UP :
					if (myTimer.isRunning() && currentDirection != 2){
						currentDirection = 0;
						moveUp();
					}
					break;
				case KeyEvent.VK_DOWN :
					if (myTimer.isRunning() && currentDirection != 0){
						currentDirection = 2;
						moveDown();
					}
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
