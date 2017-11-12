
package mapPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import mapPanel.model.Alien;
import mapPanel.model.Model;
import mapPanel.view.PopUp;

/**
 * 
 * @author jack
 *	Map panel - is added to main application frame
 */

@SuppressWarnings("serial")
public class MapPanel extends JPanel {

	
	private Image map;	
	private Model model;
	private ArrayList<JButton> alienButtons;
	
	/**
	 * This is constructor for mapPanel. Based on the given model, it creates a map 
	 * populated with alien heads on every state. Their size scales with the number
	 * of incidents in that state. Clicking them reveals more information.
	 * @param model
	 */
	public MapPanel(Model model) {
	
		
		this.model = model;
		//Create arraylist of the alien head buttons
		alienButtons = new ArrayList<JButton>();

		try {
			//Read map image from disk
			map = ImageIO.read(new File("resources/usaMap.png"));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		//Adds a border around the frame
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK)));
	}

	/**
	 * paintComponent method, draws the map background and every alien in the 
	 * arrayList of aliens to draw. Adds action listeners for the alien buttons
	 * 
	 */
	public void paintComponent(Graphics g) {
		
		//Draw the map and any aliens in list
		g.drawImage(map, 0, 0, 1144, 526, null);
		model.drawAliens(g);
		
		for(Alien alien : model.getAlienMarkers()) {
			//Initialise the JButton to be drawn
			JButton buttonToAdd = new JButton();
			//Get the size of the alien to be drawn from the Alien class
			//Will be a size between 20 and 70
			int sizeS = alien.getSize();
			//If size is less than 40, change size to 35, this prevents tiny 
			//aliens being drawn
			if (sizeS < 40) {
			buttonToAdd.setBounds(alien.getCoordinates().getX(), 
					alien.getCoordinates().getY(), 35, 35);
			} else {
				buttonToAdd.setBounds(alien.getCoordinates().getX(), 
						alien.getCoordinates().getY(), sizeS, sizeS);
			}
		
			//Add action listeners for alien
			buttonToAdd.addActionListener(new ActionListener() {
			
				@Override
				//if you click on button, pop up is created
				//Creates new PopUp frame with the incidents of every sighting for
				//the selected state
				public void actionPerformed(ActionEvent e) {
					new PopUp(model.getStateIncidents(alien.getState()),  
							alien.getState(), model).setVisible(true);
				}
				
			});
			//make the buttons transparent
			buttonToAdd.setOpaque(false);
			buttonToAdd.setContentAreaFilled(false);
			buttonToAdd.setBorderPainted(false);
			this.add(buttonToAdd);

			//add new button to list of alien buttons
			alienButtons.add(new JButton());
			
		}
		
	}

	
}
