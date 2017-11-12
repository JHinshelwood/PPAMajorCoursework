package statsPanel.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import statsPanel.controller.flipController;
import statsPanel.model.Stats;

public class StatsPanel extends JPanel {

	private static final long serialVersionUID = 2826944771373005698L;
	private static Stats statModel;
	private JPanel panels;
	private JLabel title;
	private static flipController listener;
	private ArrayList<StatsBox> boxes;
	/**
	 * The panel to display the statistics of the incidents
	 * @param model The model used to calculate the statistics
	 */
	public StatsPanel(Stats model)
	{
		super(new BorderLayout());
		statModel = model;
		init();
		setup();
	}
	
	/** 
	 * Sets up the statistic panels
	 */
	private void init()
	{
		statModel.run(); //Calculate the statistics
		boxes = new ArrayList<StatsBox>();
		for (int i = 0; i < 4; ++i)
		{
			boxes.add(new StatsBox(i+1));
		}
		panels = new JPanel(new GridLayout(2, 2, 20, 30));
		
		title = new JLabel("Statistics", JLabel.CENTER);
		title.setFont(new Font("Helvetica", Font.PLAIN, 40));
		
		for (StatsBox box : boxes)
		{
			box.setName("panel"+(boxes.indexOf(box)+1));
			panels.add(box);
		}
		addListeners();
		add(title, BorderLayout.NORTH);
		add(panels, BorderLayout.CENTER);
	}
	/** 
	 * Add listeners to the panels to allow for panel switching between statistics
	 */
	private void addListeners()
	{
		listener = new flipController(statModel);
		
		for (StatsBox box : boxes)
		{
			box.addListeners(listener);
		}
	}
	/**
	 * Sets up the initial display of each statistic based on the preference that is saved within the model.
	 * If no preference exists then it just displays the first four statistics.
	 */
	private void setup()
	{
		for (StatsBox box : boxes)
		{
			String[] data = statModel.initialDisplay(box.getID());
			box.setDisplay(data[0], data[1]);
		}
	}

	
	
	
}
