package secretPanel.model;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;


@SuppressWarnings("serial")
/**
 * This method holds all the data for the the main spaceship, which is going to be controlled by the player.
 * It can rotate, thrust and shoot.
 */
public class SpaceShip extends Polygon{

    //vectors for the ship
    private double xVelocity;
    private double yVelocity;

    //center of ship
    private double centerX;
    private double centerY;

    public static int[] polyXArray = {-13,14,-13,-5,-13};
    public static int[] polyYArray = {-15,0,15,0,-15};

    //an array list holding the missiles that will be shot
    private ArrayList<Missile> missiles;

    //width and height of the ship
    private int shipWidth;
    private int shipHeight;

    //upper left hand coordinate of the ship
    private double uLeftXPos;
    private double uLeftYPos;

    // rotation data
    private double rotationAngle = 0, movingAngle = 0;

    // game board width and height
    private int gBWidth;
    private int gBHeight;
    
    // number of lives
    private int lives;

    /**
     * Creates a new space ship, given the current game board width and height.
     *
     */
    public SpaceShip(int gBWidth, int gBHeight, int lives){

        // creates a Polygon by calling the parent class Polygon
        super(polyXArray, polyYArray, 5);

        this.gBWidth = gBWidth;
        this.gBHeight = gBHeight;

        centerX = gBWidth/2;
        centerY = gBHeight/2;;

        uLeftXPos = getXCenter() + polyXArray[0];
        uLeftYPos = getYCenter() + polyYArray[0];

        shipWidth = 27;
        shipHeight = 30;

        missiles = new ArrayList<Missile>();
        
        this.setLives(lives);
    }

    // getters and setters for the x and y center of ship

    public double getXCenter(){ return centerX; }

    public double getYCenter(){ return centerY; }

    public void setXCenter(double centerX){ this.centerX = centerX; }

    public void setYCenter(double centerY){ this.centerY = centerY; }

    public void increaseXPos(double increment) { this.centerX += increment; }

    public void increaseYPos(double increment) { this.centerY += increment; }

    // getters and setters for the x and y upper left position of the ship

    public double getuLeftXPos(){ return uLeftXPos; }

    public double getuLeftYPos(){ return uLeftYPos; }

    public void setuLeftXPos(double xPos){ this.uLeftXPos = xPos; }

    public void setuLeftYPos(double yPos){ this.uLeftYPos = yPos; }

    // getters and setters for the width and height of the ship

    public int getShipWidth(){ return shipWidth; }

    public int getShipHeight(){ return shipHeight; }

    // getter, setters, increases and decreases for the ship velocity

    public double getXVelocity(){ return xVelocity; }

    public double getYVelocity(){ return yVelocity; }

    public void setXVelocity(double value){ this.xVelocity = value; }

    public void setYVelocity(double value){ this.yVelocity = value; }

    public void increaseXVelocity(double increment){ this.xVelocity += increment; }

    public void increaseYVelocity(double increment){ this.yVelocity += increment; }

    public void decreaseXVelocity(double decrement){ this.xVelocity -= decrement; }

    public void decreaseYVelocity(double decrement){ this.yVelocity -= decrement; }

    // Set and increase the ship movement angle

    public void setMovingAngle(double moveAngle){ this.movingAngle = moveAngle; }

    public double getMovingAngle(){ return movingAngle; }

    public void increaseMovingAngle(double moveAngle){ this.movingAngle += moveAngle; }

    // Calculate the ship angle of movement

    /** ADVANCED TRIGONOMETRY BEWARE
     * Using the cos of the move angle we get the adjacent value of x.
     * (angle * pi) / 180 converts degrees to radians
     */

    public double shipXMoveAngle(double xMoveAngle){

        return (double) (Math.cos(xMoveAngle * Math.PI / 180));

    }

    //Same as before but we use sin for the y value and convert to radians
    public double shipYMoveAngle(double yMoveAngle){

        return (double) (Math.sin(yMoveAngle * Math.PI / 180));

    }


    // getter + increases or decreases the rotation angle

    public double getRotationAngle(){ return rotationAngle; }

    public void increaseRotationAngle(){

        if(getRotationAngle() >= 355) { rotationAngle = 0; }

        else { rotationAngle += 5; }

    }

    public void decreaseRotationAngle(){
        if(getRotationAngle() < 0) { rotationAngle = 355; }

        else { rotationAngle -= 5; }

    }

    // generated rectangle that we use for collision
    public Rectangle getBounds(){
        return new Rectangle((int) getXCenter() - 14,(int) getYCenter() - 14, getShipWidth(), getShipHeight());
    }

    public double getShipNoseX() {
        return this.getXCenter() + Math.cos(rotationAngle) * 14;
    }

    public double getShipNoseY() {
        return this.getYCenter() + Math.sin(rotationAngle) * 14;
    }
    
    public int getLives() { return lives; }

	public void setLives(int lives) { this.lives = lives; }


    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    /**
     * Moves and rotates the ship based on its vectors.
     */
    public void move(){
        //increase position based on velocity
        this.increaseXPos(this.getXVelocity());
        //if the ship gets out of the screen, move it to the opposite side so it reappears
        if(this.getXCenter() < 0){
            this.setXCenter(gBWidth);

        } else if (this.getXCenter() > gBWidth){
            this.setXCenter(0);
        }

        //same for Y position and bounds
        this.increaseYPos(this.getYVelocity());

        if(this.getYCenter() < 0){
            this.setYCenter(gBHeight);

        } else if (this.getYCenter() > gBHeight){
            this.setYCenter(0);
        }
    }

    /**
     * Shoots missiles based on the direction and rotation of the ship. Automatically removes missiles when not used
     * anymore after a certain threshold.
     */
    public void shoot() {
        missiles.add(new Missile(getShipNoseX(), getShipNoseY(), getRotationAngle(), gBWidth, gBHeight));

        if(missiles.size() > 50) {
            missiles.clear();
        }
        //DEBUG
        System.out.println(missiles.size());
    }

	
}