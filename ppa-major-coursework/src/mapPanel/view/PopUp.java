package mapPanel.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import api.ripley.Incident;
import api.ripley.Ripley;
import mapPanel.model.Model;
import mapPanel.model.Alien.States;
import mapPanel.model.Model.SortCriteria;

/**
 * 
 * @author jack
 *	First pop up, appears when you click on an alien head on map
 */
@SuppressWarnings("serial")
public class PopUp extends JFrame {
	private ArrayList<Incident> incidentList;
	private JComboBox<String> comboBox;
	private JList<String> incidentJList;
	private Model model;

	

	public void setIncidentList(ArrayList<Incident> list) {

		incidentList = list;
	}

	public PopUp(ArrayList<Incident> incidentList, States state, Model model) {
		this.model = model;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle(state.getLongForm() + " (" + state.toString() + ")");
		this.incidentList = model.sortBy(state, SortCriteria.POSTED);
		this.setLayout(new BorderLayout());

	
		comboBox = new JComboBox<String>(new String[] { "Posted", "City", "Shape", "Duration", "Date" });
		incidentJList = new JList<String>();

		setIncidentList(model.sortBy(state, SortCriteria.POSTED));
		incidentJList.setModel(updateListModel());

		comboBox.addActionListener(new ActionListener() {

			
			@Override
			/**
			 * Gets the selection from the jComboBox
			 * sorts the incidents by the selection
			 */
			public void actionPerformed(ActionEvent arg0) {

				if (comboBox.getSelectedItem().equals("City")) {

					setIncidentList(model.sortBy(state, SortCriteria.CITY));
					incidentJList.setModel(updateListModel());

				}

				else if (comboBox.getSelectedItem().equals("Shape")) {
					setIncidentList(model.sortBy(state, SortCriteria.SHAPE));
					incidentJList.setModel(updateListModel());

				} else if (comboBox.getSelectedItem().equals("Posted")) {
					setIncidentList(model.sortBy(state, SortCriteria.POSTED));
					incidentJList.setModel(updateListModel());

				} else if (comboBox.getSelectedItem().equals("Date")) {
					setIncidentList(model.sortBy(state, SortCriteria.DATE));
					incidentJList.setModel(updateListModel());
				
				} else if (comboBox.getSelectedItem().equals("Duration")) {
					setIncidentList(model.sortBy(state, SortCriteria.DURATION));
					incidentJList.setModel(updateListModel());
				}

			}

		});

		incidentJList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent mouseEvent) {

				JTextPane details = new JTextPane();
				JList<String> list = (JList<String>) mouseEvent.getSource();
				int index = list.locationToIndex(mouseEvent.getPoint());

				if (index >= 0) {
					displayDetails(index);
				}

			}

		});

		this.add(comboBox, BorderLayout.NORTH);
		setMinimumSize(new Dimension(900, 500));

		// this.add(incidentJList, BorderLayout.CENTER);
		this.add(new JScrollPane(incidentJList));

		pack();
	}

	/**
	 * Method to show the detail dialogue box
	 * @param index - index of selected incident
	 */
	public void displayDetails(int index) {
		String incidentID = incidentList.get(index).getIncidentID();

		// new DetailPopUp(details).setVisible(true);
		new DetailPopUp(incidentList.get(index).getSummary(), incidentID).setVisible(true);

		// JScrollPane scrollPane = new JScrollPane(msg);

	}

	/**
	 * Method to update the list model
	 * @return updated list model
	 */
	public DefaultListModel<String> updateListModel() {
		DefaultListModel<String> iModel = new DefaultListModel<String>();

		for (Incident i : incidentList) {

			iModel.addElement("Time: " + i.getDateAndTime() + ", City: " + i.getCity() + ", Shape: " + i.getShape()
					+ ", Duration : " + Model.convertDuration(i) + ", Posted: " + i.getPosted());
		
		}
		
		return iModel;
	}

	

}
