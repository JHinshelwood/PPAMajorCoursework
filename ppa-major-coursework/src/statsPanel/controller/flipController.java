package statsPanel.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import statsPanel.model.Stats;
import statsPanel.view.StatsBox;

/**
 * Controller that handles the ssitching of statistics panels
 * @author NotAPro
 *
 */
public class flipController implements ActionListener {

	private static Stats statModel;
	
	
	
	public flipController(Stats model)
	{
		statModel = model;
	}
	@Override
	/**
	 * Function gets called the user clicks on any left or right button of a StatsBox
	 */
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)(e.getSource());
		StatsBox panel = (StatsBox)(source.getParent().getParent());
		int id = panel.getID();
		String [] data;
		System.out.println(source.getName());
		if (source.getName().contains("Next"))
		{
			data = statModel.next(id);
		}
		else
		{
			data = statModel.prev(id);
		}
		panel.setDisplay(data[0], data[1]);
	}

}
