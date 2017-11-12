package secretPanel.model;

import java.util.Random;
/**
 * A type of ship that randomly changes direction and speed.
 * @author paulPC
 *
 */
@SuppressWarnings("serial")
public class AlienCruiser extends AlienShip {

	private Random rnd; 
	public AlienCruiser(int[] polyXArray, int[] polyYArray, int pointsInPoly, int randomStartPosX, int randomStartPosY,
            int gbWidth, int gBHeight, int hp) {
		super(polyXArray, polyYArray, pointsInPoly, randomStartPosX, randomStartPosY,
                gbWidth, gBHeight, hp);
		rnd = new Random();
		
		xDirection++;
		yDirection++;
	}
	
	@Override
	public void move() {
		super.move();
		if(rnd.nextInt(99) + 1 <= 10) {
			xDirection = rnd.nextInt(6) + 1;
			if(rnd.nextInt(99) + 1 <= 50) {		
				xDirection = -xDirection;
			}
		}
		
		if(rnd.nextInt(99) + 1 <= 30) {
			yDirection = rnd.nextInt(6) + 1;
			if(rnd.nextInt(99) + 1 <= 50) {
				
				yDirection = -yDirection;
			}
		}
		
		
	}
}
