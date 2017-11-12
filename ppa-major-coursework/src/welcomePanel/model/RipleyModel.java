package welcomePanel.model;

import api.ripley.Ripley;
import api.ripley.Incident;

import java.util.ArrayList;
import java.util.Observable;

/**
 * This class represents the Model, which extends Observable class. It holds all
 * the information from the ripley library.
 * @author Saadman Sakib
 */
public class RipleyModel extends Observable {

	private boolean fromComboBoxStatus, toComboBoxStatus; // Fields holding boolean values for the respective JComboBoxes.
	private int fromComboBoxValue, toComboBoxValue;       // Fields holding the values selected in the respective JComboBoxes.
	private int startYear, latestYear;                    // Fields holding the first/last year from/to which incident records are available.
	private int count;                                    // Field holding the value to track which panel the user is currently on. 
	private double versionAPI;                            // Field holding the current ripley API version.
	private Ripley ripley;                                // Field holding the ripley object of type Ripley.
	private String acknowledgementString;                 // Field holding the acknowledgement string to be shown by the program using this library, and the associated API.
	private String lastUpdatedString;                     // Field holding the last time a set of incident details were received.
	private ArrayList<Incident> incidentsInRange;         // Field holding the list of incidents that have occurred during this timeframe.
	private String iconPath;                              // Field holding the directory of the AlienIcon.png
    
	/**
     * Constructor for RipleyModel class.
     * 
     */
	public RipleyModel() {

		this.ripley = new Ripley("10tLI3CXstqyVD6ql2OMtA==", "tBgm4pRp9grVqL46EnH7ew==");
		incidentsInRange = new ArrayList<Incident>();
		iconPath = "resources/AlienIcon.png";
	}
    
	/**
	 * Void method that increments count by 1.
	 */
	public void incrementCount() {
		count = count + 1;
	}
	
	/**
	 * Void method that decrements count by 1.
	 */
	public void decrementCount() {
		count = count - 1;
	}
	
	/**
	 * Getter method for count.
	 * @return an int value which is the count.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Getter method for iconPath.
	 * @return a String which is the iconPath.
	 */
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * Void method that sets the FromComboBoxStatus to either true for (year) or false (-).
	 * @param value boolean
	 */
	public void setFromComboBoxStatus(boolean value) {
		this.fromComboBoxStatus = value;
	}

	/**
	 * Getter method for fromComboBoxStatus.
	 * @return a boolean true if a year is selected otherwise false.
	 */
	public boolean getFromComboBoxStatus() {
		return fromComboBoxStatus;
	}

	/**
	 * Void method that sets the ToComboBoxStatus to either true for (year) or false (-).
	 * @param value boolean
	 */
	public void setToComboBoxStatus(boolean value) {
		this.toComboBoxStatus = value;
	}

	/**
	 * Getter method for toComboBoxStatus.
	 * @return a boolean true if a year is selected otherwise false.
	 */
	public boolean getToComboBoxStatus() {
		return toComboBoxStatus;
	}

	/**
	 * Void method that sets the fromComboBoxValue.
	 * @param value int
	 */
	public void setFromComboBoxValue(int value) {
		this.fromComboBoxValue = value;
	}

	/**
	 * Getter method for getFromComboBoxValue.
	 * @return an int which is the fromComboBoxValue.
	 */
	public int getFromComboBoxValue() {
		return fromComboBoxValue;
	}

	/**
	 * Void method that sets the toComboBoxValue.
	 * @param value int
	 */
	public void setToComboBoxValue(int value) {
		this.toComboBoxValue = value;
	}

	/**
	 * Getter method for getToComboBoxValue.
	 * @return an int which is the toComboBoxValue.
	 */
	public int getToComboBoxValue() {
		return toComboBoxValue;
	}

	/**
	 * Getter method for acknowledgementString.
	 * @return a String which is the acknowledgementString.
	 */
	public String getAcknowledgementString() {
		this.acknowledgementString = ripley.getAcknowledgementString();
		return acknowledgementString;
	}

	/**
	 * Getter method for versionAPI.
	 * @return a double value which is the versionAPI.
	 */
	public double getVersion() {
		this.versionAPI = ripley.getVersion();
		return versionAPI;
	}
	
	/**
	 * Getter method for startYear.
	 * @return a int value which is the startYear.
	 */
	public int getStartYear() {
		this.startYear = ripley.getStartYear();
		return startYear;
	}

	/**
	 * Getter method for latestYear.
	 * @return a int value which is the latestYear.
	 */
	public int getLatestYear() {
		this.latestYear = ripley.getLatestYear();
		return latestYear;
	}

	/**
	 * Getter method for lastUpdatedString.
	 * @return a String which is the lastUpdatedString.
	 */
	public String getLastUpdated() {
		this.lastUpdatedString = ripley.getLastUpdated();
		return lastUpdatedString;
	}

	public void setIncidentsInRange(ArrayList<Incident> incidentsInRange) {
		this.incidentsInRange = incidentsInRange;
	}

	public ArrayList<Incident> getIncidents() {
		return incidentsInRange;
	}

	/**
	 * Void method that gets the incidentsInRange.
	 * @param fromYear, toYear
	 */
	public void getIncidentsInRange(String fromYear, String toYear) {

		String startDate = fromYear + "-01-01 00:00:00";
		String endDate = toYear + "-12-31 00:00:00";
		incidentsInRange = ripley.getIncidentsInRange(startDate, endDate);
	}
	
	/**
	 * Void method that checks whether to enable the JButtons which are initially disabled.
	 * Checks whether (years) are selected in the respective ComboBoxes.
	 * Calls setChanged() and notifyObservers() to make aware of the changes and notifies the View. 
	 */
	public void checkWhetherToEnableButtons() {

		if (getFromComboBoxStatus() == true && getToComboBoxStatus() == true) {
			setChanged();
			notifyObservers();
		}

	}

}
