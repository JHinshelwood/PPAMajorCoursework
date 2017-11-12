package secretPanel.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

import secretPanel.controller.ShipController;

/**
 *  Main game panel in which all the elements of the game are drawn. 
 */
@SuppressWarnings("serial")
public class GamePanel extends JComponent {

	protected int width;
	protected int height;
	
	private ShipController controller;
	private boolean canRun;

	/**
	 * Constructs a JComponent view for the game based on the given width, height and controller
	 * @param width
	 * @param height
	 * @param controller
	 */
	public GamePanel(int width, int height, ShipController controller) {
		canRun = true;
		this.width = width;
		this.height = height;
		this.controller = controller;

		setFocusable(true);

		System.out.println("test");

	}

	private int i = 0; // used for sleep
	public void paint(Graphics g) {
		
		Graphics2D graphicSettings = (Graphics2D) g;

		AffineTransform identity = new AffineTransform();

		graphicSettings.setColor(Color.BLACK);
		graphicSettings.fillRect(0, 0, getWidth(), getHeight());
		graphicSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphicSettings.setPaint(Color.WHITE);

		if (canRun) {
			controller.updateAlienShips(graphicSettings);
			controller.updatePlayer(graphicSettings, identity);
		} else { //if the player or all the ships died, loop for 100 times as a break
			if(i < 100) i++;
			else {
				start();
				i = 0;
			}
		}
		
		

	}
	
	public void start() {
		
		canRun = true;
	}

	public void stop() {
		
		canRun = false;
	}

	public boolean canRun() {
		return canRun;
	}
}
