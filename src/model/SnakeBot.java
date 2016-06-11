package model;
/**
 * Inner class representing the body of the snake.
 * @author Pwoke
 */
public class SnakeBot { //protected by default
	private int x;
	private int y;
	
	private SnakeBot next;
	private SnakeBot previous;
	
	protected SnakeBot(SnakeBot previous,int x, int y, SnakeBot next){
		this.x = x;
		this.y = y;
		this.next = next;
		this.previous = previous;
	}
	
	/**
	 * Updates the coordinates of the bot
	 * @param x the increase in x 
	 * @param y the increase in y
	 */
	public void updateX_Y(int x, int y){
		this.x = this.x + x; //set our current bot to its location.
		this.y = this.y + y;
	}
	
	public SnakeBot getPrevious() {
		return previous;
	}
	
	public void setPrevious(SnakeBot previous){
		this.previous = previous;
	}
	
	public SnakeBot getNext() {
		return next;
	}
	
	public void setNext(SnakeBot theNext) {
		next = theNext;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public void setY(int newY) {
		y = newY;
	}
	
	@Override
	public boolean equals(final Object theOther) {
		if (theOther != null && theOther instanceof SnakeBot) {
			SnakeBot other = (SnakeBot) theOther;
			
			return other.getX() == getX() && other.getY() == getY();
		}	
		return false;
	}
	
	@Override
	public String toString() {
		return x +" , "+y;
	}
}