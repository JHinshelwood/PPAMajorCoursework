package secretPanel.model;

import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * This class holds all the data for a missile type object. Similar to the other objects in this package, it has vectors
 * and dimensions.
 * It also has a moving angle, which makes the launching more realistic, instead of being a straight line.
 */
@SuppressWarnings("serial")
public class Missile extends Polygon {

    private int gbWidth;
    private int gbHeight;

    private double centerX;
    private double centerY;

    public static int[] polyXArray = {-3, 3, 3, -3, -3};
    public static int[] polyYArray = {-3, -3, 3, 3, -3};

    private int missileWidth;
    private int missileHeight;

    public boolean onScreen;

    private double movingAngle;

    private double xVelocity;
    private double yVelocity;

    public Missile(double centerX, double centerY, double movingAngle, int gbWidth, int gbHeight) {
        super(polyXArray, polyYArray, 5);

        this.gbWidth = gbWidth;
        this.gbHeight = gbHeight;

        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setMovingAngle(movingAngle);
        missileHeight = 6;
        missileWidth = 6;
        xVelocity = 5;
        yVelocity = 5;

        this.onScreen = true;


        this.setXVelocity(XMoveAngle(movingAngle) * 10);
        this.setYVelocity(YMoveAngle(movingAngle) * 10);

    }

    public double getCenterX() { return centerX; }
    public void setCenterX(double centerX) { this.centerX = centerX; }

    public double getCenterY() { return centerY; }
    public void setCenterY(double centerY) { this.centerY = centerY; }

    public void changeXPos(double increment) { this.centerX += increment; }
    public void changeYPos(double increment) { this.centerY += increment; }

    public double getXVelocity() { return xVelocity; }
    public void setXVelocity(double xVelocity) { this.xVelocity = xVelocity; }

    public double getYVelocity() { return yVelocity; }
    public void setYVelocity(double yVelocity) { this.yVelocity = yVelocity; }

    public int getWidth() { return missileWidth; }
    public int getHeight() { return missileHeight; }

    public double getMovingAngle() { return movingAngle; }
    public void setMovingAngle(double movingAngle) { this.movingAngle = movingAngle; }

    public double XMoveAngle(double xMoveAngle) {
        return (double) (Math.cos(xMoveAngle * Math.PI / 180));
    }

    public double YMoveAngle(double yMoveAngle) {
        return (double) (Math.sin(yMoveAngle * Math.PI / 180));
    }

    public Rectangle getBounds() {
        return new Rectangle((int) getCenterX() - missileWidth, (int) getCenterY() - missileHeight, getWidth(), getHeight());
    }


    /**
     * Shoots the missile from the nose of the ship
     */
    public void move() {
        if(onScreen) {
            this.changeXPos(getXVelocity());
            if(getCenterX() < 0) {
                onScreen = false;
            } else if(getCenterX() > gbWidth) {
                onScreen = false;
            }

            this.changeYPos(getYVelocity());
            if(getCenterY() < 0) {
                onScreen = false;
            } else if(getCenterY() > gbHeight) {
                onScreen = false;
            }

        }
    }


}

