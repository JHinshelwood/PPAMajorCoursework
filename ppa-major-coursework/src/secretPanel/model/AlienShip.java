package secretPanel.model;

import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * This method holds all the data of a alien ship. Its polygon shape, width and height is defined as static, resembling an UFO.
 * It has X and Y vectors, and data about the upper left corner coordinates.
 */
@SuppressWarnings("serial")
public class AlienShip extends Polygon{

    protected int uLeftXPos;
    protected int uLeftYPos;

    protected int xDirection;
    protected int yDirection;

    private static int rockWidth = 19;
    private static int rockHeight = 10;
    public static int[] sPolyXArray = { 10, 15, 16, 18, 20, 22, 23, 28, 25, 22, 16, 13, 10 };
    public static int[] sPolyYArray = { 30, 28, 27, 26, 26, 27, 28, 30, 32, 35, 35, 32, 30 };

    public boolean onScreen = false;

    //width and height of the game board on which the ship is created
    protected int gbWidth;
    protected int gBHeight;
    
    //HP of the ship
    private int hp;

    public AlienShip(int[] polyXArray, int[] polyYArray, int pointsInPoly, int randomStartPosX, int randomStartPosY,
                     int gbWidth, int gBHeight, int hp) {

        super(polyXArray, polyYArray, pointsInPoly);

        onScreen = true;
        //each new ship will have a random direction and speed

        this.xDirection = (int) (Math.random() * 4 + 1);
        this.yDirection = (int) (Math.random() * 4 + 1);

        this.uLeftXPos = randomStartPosX;
        this.uLeftYPos = randomStartPosY;

        this.gbWidth = gbWidth;
        this.gBHeight = gBHeight;
        
        this.setHp(hp);

    }

    /**
     *   Returns a rectangle with the same position and size, used for collision.
     */
    public Rectangle getBounds() {
        return new Rectangle(super.xpoints[0], super.ypoints[0], rockWidth, rockHeight);
    }
    
    public void setXDirection(int direction) { this.xDirection = direction; }
    public int getXDirection() { return xDirection; }
    
    public void setYDirection(int direction) { this.yDirection = direction; }
    public int getYDirection() { return yDirection; }
   
    /**
     * Moves the given ship on the game board. 
     * @param ship
     */
    public void move() {        
        uLeftXPos = super.xpoints[0];
        uLeftYPos = super.ypoints[0];

        if (uLeftXPos <= 0) {
        	 for (int i = 0; i < super.xpoints.length; i++) {
                 super.xpoints[i] += 5;
             }
        	 xDirection = -xDirection;
        }
        if(uLeftXPos + 25 >= gbWidth) {
        	 for (int i = 0; i < super.xpoints.length; i++) {
                 super.xpoints[i] -= 5;                
             }
        	 xDirection = -xDirection;
            
        }
        
        if (uLeftYPos <= 0) {
       	 for (int i = 0; i < super.xpoints.length; i++) {
                super.ypoints[i] += 5;
            }
       	 	yDirection = -yDirection;
       }
       if(uLeftYPos + 25 >= gBHeight) {
       	 for (int i = 0; i < super.ypoints.length; i++) {
                super.ypoints[i] -= 5;                
            }
       	 	yDirection = -yDirection;
           
       }

        for (int i = 0; i < super.xpoints.length; i++) {
            super.xpoints[i] += xDirection;
            super.ypoints[i] += yDirection;
        }
    }

    public static int[] getPolyXArray(int randomStartXPos) {
        int[] tempPolyXArray = (int[]) sPolyXArray.clone();

        for (int i = 0; i < tempPolyXArray.length; i++) {
            tempPolyXArray[i] += randomStartXPos;
        }
        return tempPolyXArray;
    }

    public static int[] getPolyYArray(int randomStartYPos) {
        int[] tempPolyYArray = (int[]) sPolyYArray.clone();

        for (int i = 0; i < tempPolyYArray.length; i++) {
            tempPolyYArray[i] += randomStartYPos;
        }
        return tempPolyYArray;
    }

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

}
