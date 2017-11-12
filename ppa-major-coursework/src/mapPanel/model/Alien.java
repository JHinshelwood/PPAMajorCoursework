package mapPanel.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;

import api.ripley.Incident;

/**
 * 
 * @author jack
 *	Alien class - contains the Image to be draw, a Jlist of incidents of the alien's
 *	state and size of the alien to be drawn
 */

public class Alien {

	//Fields for each of the alien attributes
	private States state;
	private Image alien;
	private int size;
	private JList<String> incidents;
	private static ArrayList<Incident> stateList;

	// public Alien(States state, int size) {
	public Alien(States state, int size) {

		this.state = state;
		this.size = size;
		incidents = new JList<String>();
		
		//Read alien image from disk
		try {
			alien = ImageIO.read(new File("resources/alienhead.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Getter for States enum
	 * @return state 
	 */
	public States getState() {
		return state;
	}

	/**
	 * Enum of all states
	 * @author jack
	 *
	 */
	public enum States {
		AL, AK, AZ, AR, CA, CO, CT, DE, DC, FL, GA, HI, ID, IL, IN, IA, KS, KY, LA, ME, MD, MA, MI, MN, MS, MO, MT, NE, NV, NH, NJ, NM, NY, NC, ND, OH, OK, OR, PA, RI, SC, SD, TN, TX, UT, VT, VA, WA, WI, WY;

		/**
		 * method to get coordinates of where to draw a given state
		 * @return Coordinates of where to draw the state
		 */
		public Coordinates getCoordinates() {
			switch (this) {
			case AL:
				return new Coordinates(475, 365);
			case AK:
				return new Coordinates(60, 420);
			case AZ:
				return new Coordinates(120, 310);
			case AR:
				return new Coordinates(392, 328);
			case CA:
				return new Coordinates(10, 250);
			case CO:
				return new Coordinates(210, 240);
			case CT:
				return new Coordinates(1030, 235);
			case DE:
				return new Coordinates(1005, 312);
			case DC:
				return new Coordinates(975, 300);
			case FL:
				return new Coordinates(555, 430);
			case GA:
				return new Coordinates(520, 360);
			case HI:
				return new Coordinates(180, 470);
			case ID:
				return new Coordinates(115, 140);
			case IL:
				return new Coordinates(425, 225);
			case IN:
				return new Coordinates(475, 225);
			case IA:
				return new Coordinates(365, 185);
			case KS:
				return new Coordinates(310, 250);
			case KY:
				return new Coordinates(502, 269);
			case LA:
				return new Coordinates(400, 405);
			case ME:
				return new Coordinates(1070, 120);
			case MD:
				return new Coordinates(985, 320);
			case MA:
				return new Coordinates(1035, 205);
			case MI:
				return new Coordinates(480, 160);
			case MN:
				return new Coordinates(355, 135);
			case MS:
				return new Coordinates(437, 365);
			case MO:
				return new Coordinates(390, 260);
			case MT:
				return new Coordinates(160, 80);
			case NE:
				return new Coordinates(300, 200);
			case NV:
				return new Coordinates(60, 200);
			case NH:
				return new Coordinates(1038, 172);
			case NJ:
				return new Coordinates(1005, 278);
			case NM:
				return new Coordinates(205, 320);
			case NY:
				return new Coordinates(975, 200);
			case NC:
				return new Coordinates(580, 285);
			case ND:
				return new Coordinates(280, 80);
			case OH:
				return new Coordinates(505, 210);
			case OK:
				return new Coordinates(330, 315);
			case OR:
				return new Coordinates(30, 110);
			case PA:
				return new Coordinates(925, 270);
			case RI:
				return new Coordinates(1057, 229);
			case SC:
				return new Coordinates(560, 330);
			case SD:
				return new Coordinates(280, 140);
			case TN:
				return new Coordinates(475, 302);
			case TX:
				return new Coordinates(310, 400);
			case UT:
				return new Coordinates(135, 220);
			case VT:
				return new Coordinates(1015, 170);
			case VA:
				return new Coordinates(935, 360);
			case WA:
				return new Coordinates(60, 50);
			case WI:
				return new Coordinates(425, 145);
			case WY:
				return new Coordinates(205, 165);
			}
			return null;
		}
		
		/**
		 * 
		 * @return String of the required state in long form
		 * is used in the title of the PopUp frame
		 */
		public String getLongForm() {
			switch(this) {
			case AL:
				return "Alabama";
			case AK:
				return "Alaska";
			case AZ:
				return "Arizona";
			case AR:
				return "Arksanas";
			case CA:
				return "California";
			case CO:
				return "Colorado";
			case CT:
				return "Connecticut";
			case DE:
				return "Delaware";
			case DC:
				return "Washington DC";
			case FL:
				return "Florida";
			case GA:
				return "Georgia";
			case HI:
				return "Hawaii";
			case ID:
				return "Idaho";
			case IL:
				return "Illinois";
			case IN:
				return "Indiana";
			case IA:
				return "Iowa";
			case KS:
				return "Kansas";
			case KY:
				return "Kentucky";
			case LA:
				return "Louisiana";
			case ME:
				return "Maine";
			case MD:
				return "Maryland";
			case MA:
				return "Massachusetts";
			case MI:
				return "Michigan";
			case MN:
				return "Minnisota";
			case MS:
				return "Mississippi";
			case MO:
				return "Misouri";
			case MT:
				return "Montana";
			case NE:
				return "Nebraska";
			case NV:
				return "Nevada";
			case NH:
				return "New Hampshire";
			case NJ:
				return "New Jersey";
			case NM:
				return "New Mexico";
			case NY:
				return "New York";
			case NC:
				return "North Carolina";
			case ND:
				return "North Dakota";
			case OH:
				return "Ohio";
			case OK:
				return "Oklahoma";
			case OR:
				return "Oregon";
			case PA:
				return "Pennsylvania";
			case RI:
				return "Rhode Island";
			case SC:
				return "South Carolina";
			case SD:
				return "South Dakota";
			case TN:
				return "Tennesee";
			case TX:
				return "Texas";
			case UT:
				return "Utah";
			case VT:
				return "Vermont";
			case VA:
				return "Virginia";
			case WA:
				return "Washington";
			case WI:
				return "Wisconsin";
			case WY:
				return "Wyoming";
			}
			return null;
		} 

	}

	/**
	 * Draw method for the alien
	 * call this to draw an alien image
	 * @param g
	 */
	public void draw(Graphics g) {

		//if size > 0, ie there was at least one sighting
		if (size > 0) {
			g.drawImage(alien, state.getCoordinates().getX(), 
					state.getCoordinates().getY(), size + 20, size + 20, null);
			
			JButton alienButton = new JButton();
			
			alienButton.setBounds(state.getCoordinates().getX(), 
					state.getCoordinates().getY(), size, size);
		
		}
	}
	
	/**
	 * Getter for the aliens size
	 * @return int size
	 */
	public int getSize() {
		
		return size;
	}
		
	
	/**
	 * getter for the Coordinates enum
	 * @return
	 */
	public Coordinates getCoordinates() {
		return state.getCoordinates();
	}

}
