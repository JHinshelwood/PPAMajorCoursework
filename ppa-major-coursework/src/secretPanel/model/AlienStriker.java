package secretPanel.model;

/**
 * A type of ship that moves in circular manner. 
 * @author paulPC
 *
 */
@SuppressWarnings("serial")
public class AlienStriker extends AlienShip{
	
	private double angle;
	private int angleXDirection;
	private int angleYDirection;
	
	/** Improved AlienShip with circular motion.
	 * 
	 * @param polyXArray
	 * @param polyYArray
	 * @param pointsInPoly
	 * @param randomStartPosX
	 * @param randomStartPosY
	 * @param gbWidth
	 * @param gBHeight
	 * @param hp
	 */
	public AlienStriker(int[] polyXArray, int[] polyYArray, int pointsInPoly, int randomStartPosX, int randomStartPosY,
            int gbWidth, int gBHeight, int hp) {
		super(polyXArray, polyYArray, pointsInPoly, randomStartPosX, randomStartPosY,
                     gbWidth, gBHeight, hp);
		angle = 0;
		
		angleXDirection = xDirection;
		angleYDirection = yDirection;
	}
	
	
	public void increaseAngle(){

        if(angle >= 355) { angle = 0; }

        else { angle += 0.1; }

    }

	
	@Override
	public void move() {
		 uLeftXPos = super.xpoints[0];
	        uLeftYPos = super.ypoints[0];

	        if (uLeftXPos <= 0) {
	        	 for (int i = 0; i < super.xpoints.length; i++) {
	                 super.xpoints[i] += 5;
	             }
	        	 angleXDirection  = -angleXDirection ;
	        }
	        if(uLeftXPos + 25 >= gbWidth) {
	        	 for (int i = 0; i < super.xpoints.length; i++) {
	                 super.xpoints[i] -= 5;                
	             }
	        	 angleXDirection  = -angleXDirection ;
	            
	        }
	        
	        if (uLeftYPos <= 0) {
	       	 for (int i = 0; i < super.xpoints.length; i++) {
	                super.ypoints[i] += 5;
	            }
	       	 	angleYDirection = -angleYDirection ;
	       }
	       if(uLeftYPos + 25 >= gBHeight) {
	       	 for (int i = 0; i < super.ypoints.length; i++) {
	                super.ypoints[i] -= 5;                
	            }
	       	 	angleYDirection  = -angleYDirection ;
	           
	       }
	       xDirection = angleXDirection + (int)(5 * Math.sin(angle));
	       yDirection = angleYDirection + (int)(5 * Math.cos(angle));
	       
	       increaseAngle();
		      
	        for (int i = 0; i < super.xpoints.length; i++) {
	            super.xpoints[i] += xDirection;
	            super.ypoints[i] += yDirection;
	        }
	    }
	
	
}
