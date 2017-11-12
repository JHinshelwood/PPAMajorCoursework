package welcomePanel;

import javax.swing.JPanel;

import welcomePanel.model.RipleyModel;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.Color;
import java.awt.Component;

@SuppressWarnings("serial")
public class WelcomePanel extends JPanel {

	@SuppressWarnings("unused")
	private RipleyModel ripleyModel;
	private JLabel informationLabel;

	public WelcomePanel(RipleyModel ripleyModel) {

		this.ripleyModel = ripleyModel;
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK)));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		String firstOutput = "Welcome to the Ripley Api " + ripleyModel.getVersion();
		String secondOutput = "Please select from the dates above, in order to ";
		String thirdOutput = "begin analysing UFO sighting data.";

		JLabel firstLabel = new JLabel(firstOutput);
		JLabel secondLabel = new JLabel(secondOutput);
		JLabel thirdLabel = new JLabel(thirdOutput);

		informationLabel = new JLabel("");
		informationLabel.setHorizontalAlignment(JLabel.CENTER);

		this.add(Box.createVerticalGlue());

		firstLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.add(firstLabel);
		secondLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.add(secondLabel);
		thirdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.add(thirdLabel);
		informationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.add(informationLabel);

		this.add(Box.createVerticalGlue());
	}

	public void setInformationLabel1(String fromYear, String toYear) {
		informationLabel.setText("<html> <center> <br> Date range selected, " + fromYear + " - " + toYear + "."
				+ "<br><br>" + "Grabbing data...");
	}

	public void setInformationLabel2(long minutes, long seconds) {

		if (minutes > 1 && seconds > 1) {

			informationLabel.setText(informationLabel.getText() + "<br><br>Grabbed data in " + minutes + " minutes, "
					+ seconds + " seconds." + "<br><br>" + "<b>Please now interact with this data using the</b>"
					+ "<br>" + "<b>buttons to the left and right.</b>");
		} else if (minutes > 1 && seconds < 1) {

			informationLabel.setText(informationLabel.getText() + "<br><br>Grabbed data in " + minutes + " minutes, "
					+ seconds + " second." + "<br><br>" + "<b>Please now interact with this data using the</b>" + "<br>"
					+ "<b>buttons to the left and right.</b>");
		} else if (minutes < 1 && seconds > 1) {

			informationLabel.setText(informationLabel.getText() + "<br><br>Grabbed data in " + minutes + " minute, "
					+ seconds + " seconds." + "<br><br>" + "<b>Please now interact with this data using the</b>"
					+ "<br>" + "<b>buttons to the left and right.</b>");
		} else {

			informationLabel.setText(informationLabel.getText() + "<br><br>Grabbed data in " + minutes + " minute, "
					+ seconds + " second." + "<br><br>" + "<b>Please now interact with this data using the</b>" + "<br>"
					+ "<b>buttons to the left and right.</b>");
		}

	}

}
