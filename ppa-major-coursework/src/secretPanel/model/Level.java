package secretPanel.model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 * Main model, which is a collection of alien ships plus the player ship
 */
public class Level extends Observable {

	private ArrayList<AlienShip> alienShips;
	private SpaceShip ship;
	private MotherShip martin;
	// TODO used later to implement different situations based on the level
	private int level;
	private int width, height;
	private int noOfShips;

	public Level(int width, int height, int level) {
		this.level = level;
		alienShips = new ArrayList<AlienShip>();
		this.width = width;
		this.height = height;
		Random rnd = new Random();
		
		switch(level) {
		case 1:
			ship = new SpaceShip(width, height, 10);
			martin = null;
			for (int i = 0; i < 10; i++) {
				int randomStartXPos = rnd.nextInt(width - 50) + 1;
				int randomStartYPos = rnd.nextInt(height - 50) + 1;

				alienShips.add(new AlienShip(AlienShip.getPolyXArray(randomStartXPos),
						AlienShip.getPolyYArray(randomStartYPos), 13, randomStartXPos, randomStartYPos, width, height, 1));

			}
			break;
		case 2:
			ship = new SpaceShip(width, height, 20);
			martin = null;
			for (int i = 0; i < 10; i++) {
				int randomStartXPos = rnd.nextInt(width - 50) + 1;
				int randomStartYPos = rnd.nextInt(height - 50) + 1;

				alienShips.add(new AlienStriker(AlienShip.getPolyXArray(randomStartXPos),
						AlienShip.getPolyYArray(randomStartYPos), 13, randomStartXPos, randomStartYPos, width, height, 2));

			}
			break;
		case 3:
			ship = new SpaceShip(width, height, 3);			
			martin = new MotherShip(rnd.nextInt(width -50) + 1, rnd.nextInt(height - 50) + 1, 100, 100, width, height, 10) ;

			for (int i = 0; i < 4; i++) {
				int randomStartXPos = rnd.nextInt(width - 50) + 1;
				int randomStartYPos = rnd.nextInt(height - 50) + 1;

				alienShips.add(new AlienCruiser(AlienShip.getPolyXArray(randomStartXPos),
						AlienShip.getPolyYArray(randomStartYPos), 13, randomStartXPos, randomStartYPos, width, height, 5));
		
			}
			break;		
		}

		ship = new SpaceShip(width, height, 10);
		noOfShips = alienShips.size();
		
	}

	public void updateAlienShips() {
		
		if(martin != null) martin.move(); 
		
		for (AlienShip alienShip : alienShips) {
			if (alienShip.onScreen) {
				checkCollision(ship, alienShip, martin);
				alienShip.move();

			}
		}

	}

	/**
	 * Wrapper method to preserve Law of Demeter
	 */
	public void updatePlayer() {
		ship.move();
	}

	// getters and setters
	public void setAlienShips(ArrayList<AlienShip> alienShips) {
		this.alienShips = alienShips;
	}

	public ArrayList<AlienShip> getAlienShips() {
		return alienShips;
	}

	public SpaceShip getShip() {
		return ship;
	}

	public void checkCollision(SpaceShip ship, AlienShip shipToMove, MotherShip mShip) {
		

		Rectangle shipToCheck = shipToMove.getBounds();
		
		for (AlienShip alienShip : alienShips) {

			if (alienShip.onScreen) {
				Rectangle otherShip = alienShip.getBounds();
				if (shipToMove != alienShip && otherShip.intersects(shipToCheck)) {
					int tempXDirection = shipToMove.getXDirection();
					int tempYDirection = shipToMove.getYDirection();

					shipToMove.setXDirection(alienShip.getXDirection());
					shipToMove.setYDirection(alienShip.getYDirection());

					alienShip.setXDirection(tempXDirection);
					alienShip.setYDirection(tempYDirection);

				}
				Rectangle mShipToCheck = null;
				Rectangle shipBox = ship.getBounds();
				if(mShip != null) {
					mShipToCheck = mShip.getBounds();
				}

				if (otherShip.intersects(shipBox)) {
					
					
					// TODO GameBoard.playSoundEffect(explodeFile);
					ship.setXCenter(Math.random() * width);
					ship.setYCenter(Math.random() * height);
					ship.setXVelocity(0);
					ship.setYVelocity(0);
					ship.getMissiles().clear();
					
					
					decrLives(ship);

				}
				
				if(mShip != null && mShipToCheck.intersects(shipBox)) {
						
						
						// TODO GameBoard.playSoundEffect(explodeFile);
						ship.setXCenter(Math.random() * width);
						ship.setYCenter(Math.random() * height);
						ship.setXVelocity(0);
						ship.setYVelocity(0);
						ship.getMissiles().clear();
						
						
						decrLives(ship);

					}
				
				
			
				if(mShip != null && otherShip.intersects(mShipToCheck)) {
					
					mShip.setXDirection(alienShip.getXDirection());
					mShip.setYDirection(alienShip.getYDirection());

					alienShip.setXDirection(-alienShip.getXDirection());
					alienShip.setYDirection(-alienShip.getYDirection());
				}
				
				for (Missile missile : ship.getMissiles()) {
					if (missile.onScreen) {
						if (otherShip.contains(missile.getCenterX(), missile.getCenterY())) {
							
							if(alienShip.getHp() > 0) {
								alienShip.setHp(alienShip.getHp() - 1);
							}
							
							if(alienShip.getHp() == 0) {
								alienShip.onScreen = false;
								decreaseNoOfShips();
								setChanged();
								notifyObservers();
								
							} 
							missile.onScreen = false;
							

							// TODO playSoundEffect(explodeFile);
						}
						if (mShip != null && mShipToCheck.contains(missile.getCenterX(), missile.getCenterY())) {
							
							if(mShip.getHp() > 0) {
								mShip.setHp(mShip.getHp() - 1);
							}
							
							if(mShip.getHp() == 0) {
								mShip.onScreen = false;
								setChanged();
								notifyObservers();
								
							}
							missile.onScreen = false;
							

							// TODO playSoundEffect(explodeFile);
						}
						
					}
				}

			}
		}
		
	}

	public void decrLives(SpaceShip ship) {	
		setChanged();
		notifyObservers();
		if (ship.getLives() > 0) {
			ship.setLives(ship.getLives() - 1);
		}
		
	}

	public String getLives() {
		return String.valueOf(ship.getLives());

	}

	public String getLevel() {
		return String.valueOf(level);
	}
	
	public MotherShip getMartin() {
		return martin;
	}

	public int getNoOfShips() {
		return noOfShips;
	}

	protected void decreaseNoOfShips() {
		if(noOfShips > 0) noOfShips--;
	}
}
