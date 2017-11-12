package welcomePanel.view;

import welcomePanel.WelcomePanel;
import welcomePanel.controller.JComboBoxListener;
import welcomePanel.controller.SaveListener;
import welcomePanel.model.RipleyModel;
import mapPanel.MapPanel;
import mapPanel.model.Model;
import statsPanel.view.StatsPanel;
import statsPanel.model.*;
import secretPanel.SecretPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import javax.imageio.ImageIO;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import java.util.Observer;
import java.util.Observable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class View extends JFrame implements Observer {

	private static RipleyModel ripleyModel;
	private JPanel comboBoxesPanel, buttonsPanel, contentPanel;
	private WelcomePanel welcomePanel;
	private MapPanel mapPanel;
	private StatsPanel statsPanel;
	private SecretPanel secretPanel;
	@SuppressWarnings("rawtypes")
	private JComboBox fromComboBox, toComboBox;
	private JLabel fromLabel, toLabel, lastUpdatedLabel;
	private JButton forwardButton, backButton;
	private CardLayout cardLayout = new CardLayout();

	public View(RipleyModel ripleyModel) {

		View.ripleyModel = ripleyModel;
		ripleyModel.addObserver(this);
		System.out.println("\n" + ripleyModel.getAcknowledgementString() + "\n");

		setTitle("ez money");
		setLayout(new BorderLayout());
		setSize(1200, 800);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// initComponents();

		setComboBoxesPanel();
		welcomePanel = new WelcomePanel(ripleyModel);
		setButtonsPanel();
		contentPanel = new JPanel();
		contentPanel.setLayout(cardLayout);
		contentPanel.add(welcomePanel, "1");
		cardLayout.show(contentPanel, "1");
		ripleyModel.incrementCount();
		this.add(contentPanel);

		disableButtons();
		setVisible(true);
	}

	public void initComponents() {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setComboBoxesPanel() {

		comboBoxesPanel = new JPanel();
		comboBoxesPanel.setLayout(new BorderLayout());
		fromLabel = new JLabel("From: ");
		toLabel = new JLabel("To: ");

		ArrayList<String> fromYearList = new ArrayList<String>();
		fromYearList.add("-");

		for (int years = ripleyModel.getStartYear(); years <= ripleyModel.getLatestYear(); years++) {
			fromYearList.add(String.valueOf(years));
		}

		ArrayList<String> toYearList = new ArrayList<String>();
		toYearList.add("-");

		for (int years = ripleyModel.getLatestYear(); years >= ripleyModel.getStartYear(); years--) {
			toYearList.add(String.valueOf(years));
		}

		fromComboBox = new JComboBox(fromYearList.toArray());
		fromComboBox.setSelectedIndex(0);

		toComboBox = new JComboBox(toYearList.toArray());
		toComboBox.setSelectedIndex(0);

		enableComboBoxes();

		fromComboBox.addActionListener(new JComboBoxListener(ripleyModel, "fromComboBox"));
		toComboBox.addActionListener(new JComboBoxListener(ripleyModel, "toComboBox"));
		addWindowListener(new SaveListener());

		JPanel tempPanel = new JPanel(new FlowLayout());
		tempPanel.add(fromLabel);
		tempPanel.add(fromComboBox);
		tempPanel.add(toLabel);
		tempPanel.add(toComboBox);
		comboBoxesPanel.add(tempPanel, BorderLayout.EAST);

		add(comboBoxesPanel, BorderLayout.PAGE_START);
	}

	public void setButtonsPanel() {

		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BorderLayout());

		JPanel bottomStringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		lastUpdatedLabel = new JLabel(ripleyModel.getLastUpdated());
		lastUpdatedLabel.setToolTipText("Launch the program again to refresh");

		forwardButton = new JButton(">");
		backButton = new JButton("<");

		forwardButton.addActionListener(new ButtonsListener());
		backButton.addActionListener(new ButtonsListener());
		buttonsPanel.add(backButton, BorderLayout.WEST);
		bottomStringPanel.add(lastUpdatedLabel);
		buttonsPanel.add(bottomStringPanel, BorderLayout.CENTER);
		buttonsPanel.add(forwardButton, BorderLayout.EAST);

		add(buttonsPanel, BorderLayout.PAGE_END);
	}

	public void disableButtons() {

		forwardButton.setEnabled(false);
		backButton.setEnabled(false);

		forwardButton.setToolTipText("Please select a year range first");
		backButton.setToolTipText("Please select a year range first");
	}

	public void enableButtons() {

		forwardButton.setEnabled(true);
		backButton.setEnabled(true);

		forwardButton.setToolTipText("Forward");
		backButton.setToolTipText("Back");
	}

	public void enableComboBoxes() {

		fromComboBox.setEnabled(true);
		toComboBox.setEnabled(true);

		fromComboBox.setToolTipText("Click to expand");
		toComboBox.setToolTipText("Click to expand");
	}

	public void disableComboBoxes() {

		fromComboBox.setEnabled(false);
		toComboBox.setEnabled(false);

		fromComboBox.setToolTipText(null);
		toComboBox.setToolTipText(null);
	}

	private ImageIcon setIconFromFile(String directory, int width, int height) {

		Image image = null;

		try {
			image = ImageIO.read(new File(directory));
			System.out.print("Image: AlienIcon.png icon loaded successfully! " + "Path: " + directory + "\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("Image: AlienIcon.png icon failed to load! " + "Path: " + directory + "\n");
		}
		image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}

	int init = 0;

	@Override
	public void update(Observable o, Object arg) {

		if (ripleyModel.getFromComboBoxValue() > ripleyModel.getToComboBoxValue()) {
			JOptionPane.showMessageDialog(null, "Error! Invalid year range selected. Please select a valid range.",
					"Error", JOptionPane.ERROR_MESSAGE, setIconFromFile(ripleyModel.getIconPath(), 70, 70));
		} else {

			System.out.println("I am here in the update() method.");

			String fromYear = "";
			fromYear = String.valueOf(ripleyModel.getFromComboBoxValue());
			String toYear = "";
			toYear = String.valueOf(ripleyModel.getToComboBoxValue());
			
			long time = System.currentTimeMillis();
			
			ripleyModel.getIncidentsInRange(fromYear, toYear);
			if (init == 0) {
				
				mapPanel = new MapPanel(new Model(ripleyModel.getIncidents()));
				
				statsPanel = new StatsPanel(new Stats(ripleyModel.getIncidents(), fromYear, toYear));
				secretPanel = new SecretPanel();
				contentPanel.add(mapPanel, "2");
				contentPanel.add(secretPanel, "3");
				contentPanel.add(statsPanel, "4");
				init++;
			}

			welcomePanel.setInformationLabel1(fromYear, toYear);

			time = System.currentTimeMillis() - time;

			long seconds = time/1000;
			long minutes = seconds/60;
			welcomePanel.setInformationLabel2(minutes, seconds);

			forwardButton.setEnabled(true);
			disableComboBoxes();

		}

	}

	/**
	 * The following inner class deals with the event-driven changes triggered
	 * when the user clicks the buttons.
	 */
	public class ButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("In the action performed method");
			JButton src = (JButton) e.getSource();

			if (src.equals(forwardButton)) {

				cardLayout.next(contentPanel);
				ripleyModel.incrementCount();

				if (ripleyModel.getCount() == 2 || ripleyModel.getCount() == 3) {

					enableButtons();

				} else if (ripleyModel.getCount() == 4) {

					forwardButton.setEnabled(false);
				}
			}

			else if (src.equals(backButton)) {

				cardLayout.previous(contentPanel);
				ripleyModel.decrementCount();

				if (ripleyModel.getCount() == 2 || ripleyModel.getCount() == 3) {

					enableButtons();

				} else if (ripleyModel.getCount() == 1) {

					backButton.setEnabled(false);
				}
			}
		}
	}

}