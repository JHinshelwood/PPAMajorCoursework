package secretPanel.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The ultimate ship, with impressive might and power that can not be equaled by any other type of ship. 
 * @author paulPC
 *
 */
public class MotherShip {

	private int width;
	private int height;

	protected int uLeftXPos;
	protected int uLeftYPos;

	protected int xDirection;
	protected int yDirection;

	public boolean onScreen = false;

	// width and height of the game board on which the ship is created
	protected int gbWidth;
	protected int gBHeight;

	// HP of the ship
	private int hp;
	
	
	private BufferedImage image;

	public MotherShip(int uLeftXPos, int uLeftYPos, int width, int height, int gbWidth, int gBHeight, int hp) {
		
		try {
			image = (ImageIO.read(new File("resources/mothership.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.width = width;
		this.height = height;

		this.gbWidth = gbWidth;
		this.gBHeight = gBHeight;
		this.hp = hp;

		this.xDirection = 10;
		this.yDirection = 10;


	}

	public Rectangle getBounds() {
		return new Rectangle(uLeftXPos, uLeftYPos, width, height);
	}

	public void move() {
		
		if (uLeftXPos <= 0) {
			uLeftXPos += 5;
			xDirection = -xDirection;
		}
		if (uLeftXPos + 100 >= gbWidth) {
			uLeftXPos -= 5;
			xDirection = -xDirection;

		}

		if (uLeftYPos <= 0) {
			uLeftYPos += 5;
			yDirection = -yDirection;
		}
		if (uLeftYPos + 100 >= gBHeight) {
			uLeftYPos -= 5;
			yDirection = -yDirection;

		}

		uLeftXPos += xDirection;
		uLeftYPos += yDirection;

		// set image position

	}

	public void setXDirection(int direction) {
		this.xDirection = direction;
	}

	public int getXDirection() {
		return xDirection;
	}

	public void setYDirection(int direction) {
		this.yDirection = direction;
	}

	public int getYDirection() {
		return yDirection;
	}

	public int getULeftXPos() { return uLeftXPos; }
	public int getULeftYPos() { return uLeftYPos; }

	public BufferedImage getImage() {
		return image;
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}


}