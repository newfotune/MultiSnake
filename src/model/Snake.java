package model;

public class Snake {
	
	private SnakeBot head;
	private int size;
	private int maxLength;
	
	public Snake(int startX, int startY, int maxLength) {
		head = new SnakeBot(null, startX, startY, null);
		head.setNext(head);
		head.setPrevious(head);

		addBody(head.getX()+1, head.getY());
		addBody(head.getX()+2, head.getY());
		addBody(head.getX()+3, head.getY());
		addBody(head.getX()+4, head.getY());
		addBody(head.getX()+5, head.getY());
		addBody(head.getX()+6, head.getY());
		addBody(head.getX()+7, head.getY());
		addBody(head.getX()+8, head.getY());
		addBody(head.getX()+9, head.getY());
	}
	
	/**
	 * Adds a new bot to the snake's body.
	 */
	public void addBody(int x, int y) {
		SnakeBot curr = new SnakeBot(null, x, y, null);
		
		curr.setPrevious(head.getPrevious());
		curr.setNext(head);
		head.setPrevious(curr);
		curr.getPrevious().setNext(curr);
	}
	
	public int getSize() {
		return size;
	}
	/**
	 * Return true if the size of the snake is at its maximum.
	 * @return true if size is greater then or equal to maxLength
	 */
	public boolean isMaxed() {
		return size >= maxLength;
	}
	
	/**
	 * Moves the head of the snake to the new direction.
	 * @param x the new x location of the snake head
	 * @param y the new y location of the snake head.
	 */
	public void move(int x, int y) {
		SnakeBot curr = head.getPrevious();
		
		while (!curr.equals(head)) {
			curr.setX(curr.getPrevious().getX());
			curr.setY(curr.getPrevious().getY());
			curr = curr.getPrevious();
		}
		
		curr.updateX_Y(x, y);
		head = curr;
	}
	
	public SnakeBot getSnake() {
		return head;
	}
}
