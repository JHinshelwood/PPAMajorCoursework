package mapPanel.model;

/**
 * 
 * @author jack
 *	Model of a coordinate set
 */
public class Coordinates {
	
	//fields for x and y position
	private int x;   
	private int y;   
	
	/**
	 * 
	 * @param x x positition to be supplied
	 * @param y y position to be supplied
	 */
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @param x set x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * 
	 * @param y set y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * 
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}
	
	
	/**
	 * 
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @return [x, y]
	 */
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
	
}
