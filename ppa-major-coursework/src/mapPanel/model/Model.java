package mapPanel.model;

import java.awt.Graphics;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.ripley.Incident;
import api.ripley.Ripley;
import mapPanel.model.Alien.States;

/**
 * 
 * @author jack
 *	Model class for the map panel
 */
public class Model {

	//Fields for the list of incidents, a hashmap of states linked with
	//the number of sightings, arraylist of alien markers
	private ArrayList<Incident> list;
	private static HashMap<String, Integer> sightings;
	private ArrayList<Alien> alienMarkers;

	/**
	 * enum of ways to sort the incident list of a given state
	 * @author jack
	 *
	 */
	public static enum SortCriteria {
		CITY, DATE, POSTED, DURATION, SHAPE

	}

	/**
	 * Constructor for model class
	 * @param list - this list is the ArrayList of incidents in a given range
	 * From this list we create the sightings hashmap
	 */
	public Model(ArrayList<Incident> list) {
		this.list = list;
		//creating sightings hashmap
		sightings = new HashMap<String, Integer>();
		
		getIncidentsInRange();
		//generate alien drawing markers
		generateMarkers();
	}

	/**
	 * 
	 * @param state
	 * @return arraylist of incidents for the supplied state
	 */
	public ArrayList<Incident> getStateIncidents(States state) {
		ArrayList<Incident> arrToReturn = new ArrayList<Incident>();

		for (Incident incident : list) {
			if (incident.getState().equals(state.toString())) {
				arrToReturn.add(incident);
			}
		}

		return arrToReturn;
	}

	/**
	 * Get incidents in range method
	 * clears the sightings hashmap, repopulates it with incidents
	 */
	public void getIncidentsInRange() {
		sightings.clear();
		if (list != null) {
			for (Incident i : list) {
				String state = i.getState();
				//if sightings has the key for this state
				if (sightings.containsKey(state)) {
					//store how many sightings were in the state map
					int stateSightings = sightings.get(state);
					//merge washington DC into Maryland
					if (state.equals("DC")) {
						sightings.put("MD", stateSightings + 1);
					//incremement number of sightings by 1
					} else {
						sightings.put(i.getState(), stateSightings + 1);
					}
					//else create a map for the state
				} else {
					sightings.put(state, 1);
				}
			}
			//call calculate size method, creates the sizes for the aliens
			calculateSize();
		}

	}

	/**
	 * Populates the array of markers with proper alien objects based on the
	 * states in the hashmap.
	 */
	private void generateMarkers() {

		alienMarkers = new ArrayList<Alien>();

		// convert hashmap values to an array (could use set and foreach loops
		// too)
		String[] sightingsArray = sightings.keySet().toArray(new String[sightings.size()]);
		for (int i = 0; i < sightingsArray.length; i++) {
			// initial state, set to null
			States generatedState = null;
			// loop through the available states
			for (States state : States.values()) {
				// compare the sighting state with the existing states
				if (sightingsArray[i].equals(state.toString())) {
					// set the generated state to that
					generatedState = state;
				}
			}
			// if the state is not null(i.e there is a valid state), create a
			// new alien based on the data in model
			if (generatedState != null) {
				alienMarkers.add(new Alien(generatedState, sightings.get(generatedState.toString())));
			}
		}

	}

	/**
	 * 
	 * @param g
	 * method to draw every element in the list of things ot draw
	 */
	 
	public void drawButtons(Graphics g) {
		for (Alien alien : alienMarkers)
			alien.draw(g);
	}

	/**
	 * This method draws all the aliens in the ArrayList of alienMarkers
	 * 
	 * @param g
	 */
	public void drawAliens(Graphics g) {
		for (Alien alien : alienMarkers)
			alien.draw(g);
	}

	/**
	 * method to calculate the size of the alien to be drawn
	 * itterates though the keyset of sightings to find state with highest number 
	 * of sightings
	 */
	public void calculateSize() {
		int max = 0;
		for (String s : sightings.keySet()) {
			if (sightings.get(s) > max) {
				max = sightings.get(s);
			}
		}

		for (String s : sightings.keySet()) {
			int stateI = sightings.get(s);
			Double stateP = (stateI * 50.0 / max);
			stateI = stateP.intValue();
			sightings.put(s, stateI);
		}
	}

	/**
	 * 
	 * @return Hashmap of sightings
	 */
	public HashMap<String, Integer> getSightings() {
		return sightings;
	}

	/**
	 * 
	 * @param s string representation fo abbrieviated state, eg TX for texas
	 * @return number of sightings
	 */
	public int getStateSightings(String s) {
		return sightings.get(s);
	}

	/**
	 * 
	 * @author jack
	 *	Class to compare list of incidents by shape
	 */
	public static class ShapeComparator implements Comparator<Incident> {
		@Override
		public int compare(Incident o1, Incident o2) {
			return o1.getShape().compareTo(o2.getShape());
		}
	}

	/**
	 *	Class to compare list of incidents by city
	 * @author jack
	 *
	 */
	public static class CityComparator implements Comparator<Incident> {
		@Override
		public int compare(Incident o1, Incident o2) {
			return o1.getCity().compareTo(o2.getCity());
		}
	}

	/**
	 * Class to compare list of incidents by date posted

	 * @author jack
	 *
	 */
	public static class PostedComparator implements Comparator<Incident> {
		@Override
		public int compare(Incident o1, Incident o2) {
			return o1.getPosted().compareTo(o2.getPosted());
		}
	}

	/**
	 * Class to compare list of incidents by Date observed
	 * @author jack
	 *
	 */
	public static class DateComparator implements Comparator<Incident> {
		@Override
		public int compare(Incident o1, Incident o2) {
			return o1.getDateAndTime().compareTo(o2.getDateAndTime());
		}
	}

	/**
	 * Class to compare list of incidents by duration

	 * @author jack
	 *
	 */
	public static class DurationComparator implements Comparator<Incident> {
		@Override
		public int compare(Incident o1, Incident o2) {
			if (convertDuration(o1) < convertDuration(o2)) return -1;
			if (convertDuration(o1) == convertDuration(o2)) return 0;
			if (convertDuration(o1) > convertDuration(o2) || (convertDuration(o1) == -1)) return 1;
			else return 9;
				
			}
					//o1.getDuration().compareTo(o2.getDuration());
			
		}
	
	/**
	 * 
	 * USE THIS METHOD TO PARSE THE DURATION AND RETURN AN INTEGER NUMBER EG A
	 * RETURN VALUE OF 44 IS 44 MINUTES
	 * 
	 * @param i - an incident
	 */
	
	public static int convertDuration(Incident i) {
		String duration = i.getDuration();
		Pattern digitPattern = Pattern.compile("\\d(\\d*)?");
		Pattern hourPattern = Pattern.compile("(H|h)(our)(s)?");
		Pattern minutesPattern = Pattern.compile("(M|m)(inute)(s)?");
		Pattern secondsPattern = Pattern.compile("(S|s)(econd)(s)?");

		Matcher myMatcher = digitPattern.matcher(duration);
		Matcher myHourMatcher = hourPattern.matcher(duration);
		Matcher myMinuteMatcher = minutesPattern.matcher(duration);
		Matcher mySecondMatcher = secondsPattern.matcher(duration);

		if (myMatcher.find()) {
			if (myHourMatcher.find()) {
				String result = myMatcher.group();
				int finalResult = Integer.parseInt(result);
				return finalResult * 60;

			} else if (myMinuteMatcher.find()) {
				String result = myMatcher.group();
				int finalResult = Integer.parseInt(result);
				return finalResult;
				
				
			} else if (mySecondMatcher.find()) {
				String result = myMatcher.group();
				int finalResult = Integer.parseInt(result);
				return finalResult / 60;
				}
		}
		return -1;
	}

	/**
	 * 
	 * @param state state to sort
	 * @param sortCriteria duration, city, shape etc
	 * @return sorted arraylist
	 */
	public ArrayList<Incident> sortBy(States state, SortCriteria sortCriteria) {

		ArrayList<Incident> arrToReturn = getStateIncidents(state);

		switch (sortCriteria) {
		case CITY:
			Collections.sort(arrToReturn, new CityComparator());
			break;

		case DATE:
			Collections.sort(arrToReturn, new DateComparator());
			break;

		case DURATION:
			
			Collections.sort(arrToReturn, new DurationComparator());
			break;

		case POSTED:
			Collections.sort(arrToReturn, new PostedComparator());
			break;

		case SHAPE:
			Collections.sort(arrToReturn, new ShapeComparator());
			break;
		}

		return arrToReturn;

	}

	/**
	 * 
	 * @return alien marker arraylist
	 */
	public ArrayList<Alien> getAlienMarkers() {
		return alienMarkers;
	}
}
