package welcomePanel.controller;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import welcomePanel.model.RipleyModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JComboBoxListener implements ActionListener {

	private RipleyModel ripleyModel;
	private String stringObj;

	public JComboBoxListener(RipleyModel ripleyModel, String stringObj) {
		this.ripleyModel = ripleyModel;
		this.stringObj = stringObj;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int comboBoxValue1 = 0;
		int comboBoxValue2 = 0;
		String selectedString1 = "";
		String selectedString2 = "";

		if (e.getSource() instanceof JComboBox) {

			if (stringObj.equals("fromComboBox")) {
				System.out.println("Here in the <FROM> comboBox action");
				@SuppressWarnings("rawtypes")
				JComboBox fromComboBox = (JComboBox) e.getSource();
				Object selected = fromComboBox.getSelectedItem();
				selectedString1 = selected.toString();
				if (selectedString1.equals("-")) {
					JOptionPane.showMessageDialog(null, "Please select a year and try again.", "Error", JOptionPane.ERROR_MESSAGE);
					ripleyModel.setFromComboBoxStatus(false);
					System.out.println("[FALSE] The status of the <FROM> comboBox is " + ripleyModel.getFromComboBoxStatus() + ".");
				} else {
					comboBoxValue1 = Integer.parseInt(selectedString1);
					System.out.println("The comboBoxValue of the <FROM> comboBox is " + comboBoxValue1);
					ripleyModel.setFromComboBoxValue(comboBoxValue1);
					ripleyModel.setFromComboBoxStatus(true);
					System.out.println("[TRUE] The status of the <FROM> comboBox is " + ripleyModel.getFromComboBoxStatus() + ".");
				}
			}

			else if (stringObj.equals("toComboBox")) {
				System.out.println("Here in the <TO> comboBox action.");
				@SuppressWarnings("rawtypes")
				JComboBox toComboBox = (JComboBox) e.getSource();
				Object selected2 = toComboBox.getSelectedItem();
				selectedString2 = selected2.toString();
				if (selectedString2.equals("-")) {
					JOptionPane.showMessageDialog(null, "Please select a year and try again.", "Error", JOptionPane.ERROR_MESSAGE);
					ripleyModel.setToComboBoxStatus(false);
					System.out.println("[FALSE] The status of the <TO> comboBox is " + ripleyModel.getToComboBoxStatus() + ".");
				} else {
					comboBoxValue2 = Integer.parseInt(selectedString2);
					System.out.println("The comboBoxValue of the <TO> comboBox is " + comboBoxValue2);
					ripleyModel.setToComboBoxValue(comboBoxValue2);
					ripleyModel.setToComboBoxStatus(true);
					System.out.println("[TRUE] The status of the <TO> comboBox is " + ripleyModel.getToComboBoxStatus() + ".");
				}
			}
		}

		ripleyModel.checkWhetherToEnableButtons();
	}

}
