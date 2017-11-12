package secretPanel;

import secretPanel.view.View;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author Saadman
 *
 */
@SuppressWarnings("serial")
public class SecretPanel extends JPanel {

	private JLabel gameTitleLabel;
	private JLabel gameInstructionLabel;
    private JLabel gameTipsLabel;
	
	public SecretPanel() {

		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK)));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JButton launchButton = new JButton("Good Luck!");
        launchButton.setToolTipText("Launch Game");
		
        gameTitleLabel = new JLabel();
		gameInstructionLabel = new JLabel();
        gameTipsLabel = new JLabel();
		
		gameTitleLabel.setFont(gameTitleLabel.getFont().deriveFont(25.0f));

		setGameTitleLabel();
		setGameInstructionLabel();
		setGameTipsLabel();
		
		gameTitleLabel.setHorizontalAlignment(JLabel.CENTER);
		gameInstructionLabel.setHorizontalAlignment(JLabel.CENTER);
		gameTipsLabel.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(Box.createVerticalGlue());

		gameTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(gameTitleLabel);

		gameInstructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(gameInstructionLabel);
		
		gameTipsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(gameTipsLabel);

		launchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(launchButton);

		this.add(Box.createVerticalGlue());

		launchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new View().setVisible(true);
			}
		});
	}

	public void setGameTitleLabel() {
		gameTitleLabel.setText(gameTitleLabel.getText() + "<html> <center> <b>UFO Hunt</b> <br><br>");
	}

	public JLabel getGameTitleLabel() {
		return gameTitleLabel;
	}

	public void setGameInstructionLabel() {
		gameInstructionLabel.setText(gameInstructionLabel.getText()
				+ "<html> <center> Your goal is to hunt down the UFOs trying to attack our planet. <br>" + "<br>"
				+ "You are being set in charge of the ship that can destroy them. <br>" + "<br>"
				+ "You can maneuver the ship and shoot using <b>W</b>, <b>A</b>, <b>D</b> and <b>ENTER</b>"
				+ "." + " <br>" + "<br>"
				+ "Maneuvering is difficult as it is simulating space conditions, hence no brakes! <br><br>");
	}

	public JLabel getGameInstructionlabel() {
		return gameInstructionLabel;
	}

	public void setGameTipsLabel() {
		gameTipsLabel.setText(gameTipsLabel.getText() + "<html> <b>Tips:</b> <br>" + "<br>"
	    + "- Kill all of the UFOs in order to proceed through the levels. Level 3 holds a surprise! <br>"
		+ "- Hold <b>ENTER</b> to go into a focused shot which increases missile speed but only works while stationary. <br>"
		+ "- Focused shot can also be used while spinning in any direction. <br><br><br><br>");
	}

	public JLabel getGameTipsLabel() {
		return gameTipsLabel;
	}

}
