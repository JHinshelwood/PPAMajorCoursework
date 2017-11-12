package driver;

import welcomePanel.controller.JComboBoxListener;
import welcomePanel.model.RipleyModel;
import welcomePanel.view.View;


public class Driver {

	public static void main(String[] args) {
		
		RipleyModel ripleyModel = new RipleyModel();
		JComboBoxListener controller = new JComboBoxListener(ripleyModel, "");
		View gui = new View(ripleyModel);
	}

}
