package statsPanel.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.*;

import javax.net.ssl.HttpsURLConnection;

import api.ripley.*;

public class Stats  {

	private SimpleDateFormat full;
	private ArrayList<Incident> incidents;
	private Statistic hoaxes, nonUS, YTsightings, commonState, avgDur, commonHours, commonShape, commonCity;
	private ArrayList<String> stateList, cities;
	private HashMap<String, Integer> states;
	private ArrayList<Statistic> data;
	private static HashMap<Integer, Integer> displayed;
	private String startYear, endYear;
	private final String key = "AIzaSyBdYrPydgjk2ijvCsIPxZlv4sAKBMLyKYY";
	private final String query = "reported+alien|ufo+sightings";
	private final int MAX_STATISTICS = 8;
	/**
	 * Creates a new statistics model
	 */
	public Stats(ArrayList<Incident> incidents, String startYear, String endYear)
	{
		states();
		data = new ArrayList<Statistic>();
		states = new HashMap<String, Integer>();
		displayed = new HashMap<Integer, Integer>();
		cities = new ArrayList<String>();
		full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		setIncidents(incidents);
		setYearRange(startYear, endYear);
		defaultDisplay();
		statSetup();
	}
	/**
	 * Calculates and gathers the statistics for display in the view
	 */
	public void run() {
		getData();
		getPreferences();
	}
	/**
	 * Instantiates each statistic and sets the titles
	 */
	private void statSetup()
	{
		hoaxes = new Statistic("Hoaxes");
		nonUS = new Statistic("Number of Non-US incidents");
		commonState = new Statistic("US state with the largest number of incidents");
		YTsightings = new Statistic("Number youtube videos related to incidents");
		avgDur = new Statistic("Average duration of each incident");
		commonHours = new Statistic("Most common hours for incidents to occur");
		commonShape = new Statistic("Most common shape");
		commonCity = new Statistic("Non-US place with largest number of incidents");
	}
	/**
	 * Sets up the map for the default display.
	 * The first panel displayed the 1st statistic, the 2nd panel displays he 2nd statistic, etc. such that
	 * the first four statistics are displayed
	 */
	private void defaultDisplay()
	{
		displayed.put(1, 1);
		displayed.put(2, 2);
		displayed.put(3, 3);
		displayed.put(4, 4);
	}
	/**
	 * Returns the raw data of a statistic in String form. 
	 * @param n The Index of the statistic in the data ArrayList.
	 * @return The raw data of the statistic
	 */
	public String displayData(int n)
	{
		--n;
		return String.valueOf(data.get(n).getStat());
	}
	/**
	 * Gets the title of a statistic.
	 * @param n The Index of the statistic in the data ArrayList.
	 * @return The title of the statistic
	 */
	public String displayTitle(int n)
	{
		--n;
		return data.get(n).getName();
	}
	
	/**
	 * Retrieves the initial title and raw data of a certain statistics panel.
	 * @param n The ID of the panel
	 * @return An array containing the title and data in the first and second index respectively
	 */
	public String[] initialDisplay(int n)
	{
		n = displayed.get(n);
		return new String[] {displayTitle(n), displayData(n)};
	}
	
	
	/**
	 * Counts the amount of youtube videos related to ufo and alien sightings within the giving time frame.
	 */
	private void youtubeSightings()
	{
		if (Integer.parseInt(endYear) < 2005)
		{
			YTsightings.setStat(0); return;
		}
		HttpsURLConnection con = null;
		final String start = startYear+"-01-01T00:00:00Z", end = endYear+"-12-31T23:59:59Z";
		final String apiURL = "https://www.googleapis.com/youtube/v3/search?key="+key+
				"&part=snippet&q="+query+
				"&type=video&maxResults=0"
				+ "&publishedAfter="+start+"&publishedBefore="+end;
		try {
			URL url = new URL(apiURL);
			con = (HttpsURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-length", "0");
			con.setUseCaches(false);
			con.setAllowUserInteraction(false);
			con.setConnectTimeout(10000);
			con.setReadTimeout(0);
			con.connect();
			int n = con.getResponseCode();
			if (n == 201 || n == 200)
			{
				//successful connection
				
				//Retrieve the JSON data from the connection
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                JSONObject json = new JSONObject(sb.toString());
                YTsightings.setStat(json.getJSONObject("pageInfo").getInt("totalResults"));
			}
			else
			{
				print("Error code: "+n);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (con!=null)
			{
				con.disconnect();
			}
		}
		
		
	}
	/**
	 * Retrieves the next available statistic that is not currently being shown by the panel
	 * @param panelID ID of the panel that needs the next statistic
	 * @return An array containing the title and data of the next statistic
	 */
	public String[] next(int panelID)
	{
		int n = displayed.get(panelID);
		print("Next called. ID: "+panelID+" data: "+n);
		if (n==MAX_STATISTICS)n=1;
		while (displayed.containsValue(n))
		{
			if (n==MAX_STATISTICS)
			{
				n = 1;
			}
			else
			{
				++n;
			}
			
		}
		print("Out of loop with "+n);
		displayed.put(panelID, n);
		print(displayed.toString());
		return new String[] {displayTitle(n), displayData(n)};
	}
	/**
	 * Retrieves the previous available statistic that is not currently being shown by the panel
	 * @param panelID ID of the panel that needs the previous statistic
	 * @return An array containing the title and data of the next statistic
	 */
	public String[] prev(int panelID)
	{
		int n = displayed.get(panelID);
		if (n==1)n=MAX_STATISTICS;
		print("Prev called. ID: "+panelID+" data: "+n);
		while (displayed.containsValue(n))
		{
			if (n==1)
			{
				n=MAX_STATISTICS;
			}
			else
			{
				--n;
			}
		}
		print("Out of loop with "+n);
		displayed.put(panelID, n);
		print(displayed.toString());
		return new String[] {displayTitle(n), displayData(n)};
	}
	/**
	 * Gets the preferences for the shown statistics from a serialized file
	 */
	@SuppressWarnings("unchecked")
	private void getPreferences()
	{
		try {
			FileInputStream fileIn = new FileInputStream("resources/statPrefs.ser");
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			displayed = (HashMap<Integer, Integer>) objIn.readObject();
			objIn.close(); fileIn.close();
		}
		catch (IOException e)
		{
			print("No preferences saved yet");
			savePreferences();
			print(displayed);
		}
		catch (ClassNotFoundException e2)
		{
			e2.printStackTrace();
		}
	}
	/**
	 * Saves the preferred statistics to be displayed via serialization
	 */
	public static void savePreferences()
	{
		try {
			FileOutputStream fileOut = new FileOutputStream("resources/statPrefs.ser");
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(displayed);
			objOut.close(); fileOut.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Calculates all of the statistics and then adds them to the array of statistics
	 */
	private void getData()
	{
		countNonUSCities(); countHoaxes(); stateMode(); averageDuration();
		youtubeSightings(); commonHours(); commonShape();
		data.add(hoaxes);
		data.add(nonUS);
		data.add(commonState);
		data.add(YTsightings);
		data.add(avgDur);
		data.add(commonHours);
		data.add(commonShape);
		data.add(commonCity);
		
	}
	/**
	 * Calculates the most common shape of all the incidents
	 */
	private void commonShape()
	{
		String shape = "";
		HashMap<String, Integer> shapes = new HashMap<String, Integer>();
		
		for (Incident i : incidents)
		{
			shape = i.getShape();
			shapes.put(shape, shapes.get(shape) == null ? 1 : shapes.get(shape) + 1);
		}
		int i = 0;
		for (Entry<String, Integer> e: shapes.entrySet())
		{
			if (e.getValue() > i)
			{
				shape = e.getKey();
				i = e.getValue();
			}
		}
		
		commonShape.setStat(shape);
				
	}
	/**
	 * Calculates the most common time of the day that incidents happen
	 */
	private void commonHours()
	{
		SimpleDateFormat hour = new SimpleDateFormat("HH");
		HashMap<String, Integer> tally = new HashMap<String, Integer>();
		int h = 0; String time = "";
		tally.put("Between 04:00 and 12:00", 0);
		tally.put("Between 12:00 and 20:00", 0);
		tally.put("Between 20:00 and 04:00", 0);
		for (Incident i : incidents)
		{
			try {
				h = Integer.parseInt(hour.format(full.parse(i.getDateAndTime())));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (h >= 4 && h < 12)
			{
				time = "Between 04:00 and 12:00";
			}
			else if (h >= 12 && h < 20)
			{
				time = "Between 12:00 and 20:00";
			}
			else 
			{
				time = "Between 20:00 and 04:00";
			}
				
			tally.put(time, tally.get(time) + 1);
		}
		h = 0;
		for (Entry<String, Integer> e : tally.entrySet())
		{
			if (e.getValue() > h)
			{
				h = e.getValue();
				time = e.getKey();
			}
		}
		commonHours.setStat(time);
	}
	/**
	 * Counts the amount of suspected hoaxes of all the incidents
	 */
	private void countHoaxes()
	{
		int n = 0;
		for (Incident in : incidents)
		{
			if (in.getSummary().toLowerCase().contains("hoax")) ++n;
		}
		hoaxes.setStat(n);
	}
	/**
	 * Counts how many incidents occur outside of the united states
	 */
	private void countNonUSCities()
	{
		//Matcher m;
		int subtract=0;
		for (Incident i : incidents)
		{
			cities.add(i.getState());
		}
		for (String s : cities)
		{
			//m = Pattern.compile(cityRegex, Pattern.CASE_INSENSITIVE).matcher(s);
			for (String st : stateList)
			{
				if (st.contains(s)) {
					++subtract;states.put(st, states.get(st) == null ? 1 : states.get(st) + 1);
				}
				
			}
		}
		nonUS.setStat(cities.size() - subtract);
	}
	/**
	 * Finds the US state that has the highest number of incidents
	 */
	private void stateMode()
	{
		int max = 0; String str = "";
		if (states.size() == 0) 
			{
				commonState.setStat("No US occurrences");
				return;
			}
		for (Entry<String, Integer> e : states.entrySet())
		{
			if (e.getValue() > max)
			{
				str = e.getKey();
				max = e.getValue();
			}
			else if (e.getValue() == max && max > 1)
			{
				if (e.getKey().compareTo(str) < 0)
				{
					str = e.getKey();
				}
			}
		}
		Scanner s = new Scanner(str);
		s.useDelimiter(",");
		if (s.hasNext()) commonState.setStat(s.next());
		s.close();
	}
	/**
	 * Calculates the average duration of all of the incidents
	 */
	private void averageDuration()
	{
		String range = "(\\d+)(?:-|\\sto\\s)(\\d+)";
		String timeUnit = "(minutes?|seconds?|hours?|mins?|secs?|hrs?)";
		String hours = "(\\d+|\\d+\\.\\d+|\\.\\d+)\\s?(?:hours?|hrs?)";
		String moreThan = "(\\d+)\\+";
		String minutes = "(\\d+|\\d+\\.\\d+|\\.\\d+)\\s?(?:minutes?|mins?)";
		String seconds = "(\\d+|\\d+\\.\\d+|\\.\\d+)\\s?(?:seconds?|secs?)";
		String arbitrary = "(several|few|many|couple)";
		
		Matcher rangeMatcher, timeUnitMatcher, hoursMatcher, minutesMatcher, secondsMatcher, arbitraryMatcher, moreThanMatcher;
		
		Pattern rangePattern = Pattern.compile(range, Pattern.CASE_INSENSITIVE);
		Pattern timeUnitPattern = Pattern.compile(timeUnit, Pattern.CASE_INSENSITIVE);
		Pattern hoursPattern = Pattern.compile(hours, Pattern.CASE_INSENSITIVE);
		Pattern minutesPattern = Pattern.compile(minutes, Pattern.CASE_INSENSITIVE);
		Pattern secondsPattern = Pattern.compile(seconds, Pattern.CASE_INSENSITIVE);
		Pattern arbitraryPattern = Pattern.compile(arbitrary, Pattern.CASE_INSENSITIVE);
		Pattern moreThanPattern = Pattern.compile(moreThan, Pattern.CASE_INSENSITIVE);
		
		ArrayList<String> arbList = new ArrayList<String>();
		ArrayList<String> rangeList = new ArrayList<String>();
		ArrayList<String> hoursList = new ArrayList<String>();
		ArrayList<String> minutesList = new ArrayList<String>();
		ArrayList<String> secondsList = new ArrayList<String>();
		ArrayList<String> moreThanList = new ArrayList<String>();
		ArrayList<String> noneList = new ArrayList<String>();
		
		ArrayList<String> durations = new ArrayList<String>();
		double avg = 0;
		for (Incident i : incidents)
		{
			durations.add(i.getDuration());
		}
		
		for (String d : durations)
		{
			rangeMatcher = rangePattern.matcher(d);
			timeUnitMatcher = timeUnitPattern.matcher(d);
			hoursMatcher = hoursPattern.matcher(d);
			minutesMatcher = minutesPattern.matcher(d);
			secondsMatcher = secondsPattern.matcher(d);
			arbitraryMatcher = arbitraryPattern.matcher(d);
			moreThanMatcher = moreThanPattern.matcher(d);
			
			if (arbitraryMatcher.find())
			{
				if (timeUnitMatcher.find())
				{
					arbList.add(d);
					avg += 5 * timeUnitToNum(timeUnitMatcher.group());
				}
			}
			else if (rangeMatcher.find())
			{
				if (timeUnitMatcher.find())
				{
					rangeList.add(d);
					avg +=  ((Double.parseDouble(rangeMatcher.group(1)) + Double.parseDouble(rangeMatcher.group(2)))/2) * timeUnitToNum(timeUnitMatcher.group());
				}
			}
			else if (hoursMatcher.find())
			{
				hoursList.add(d);
				avg += Double.parseDouble(hoursMatcher.group(1)) * 3600;
			}
			else if (minutesMatcher.find())
			{
				minutesList.add(d);
				avg += Double.parseDouble(minutesMatcher.group(1)) * 60;
			}
			else if (secondsMatcher.find())
			{
				secondsList.add(d);
				avg += Double.parseDouble(secondsMatcher.group(1));
			}
			else if (moreThanMatcher.find())
			{
				if (timeUnitMatcher.find())
				{
					moreThanList.add(d);
					avg += Double.parseDouble(moreThanMatcher.group(1)) * timeUnitToNum(timeUnitMatcher.group());
				}
			}
			else if (d.toLowerCase().contains("all night"))
			{
				avg += 28800; //8 hours in seconds
			}
			else if (d.toLowerCase().contains("half hour") || d.toLowerCase().contains("half an hour"))
			{
				avg += 1800; //30 mins in seconds
			}
			else
			{
				noneList.add(d);
			}
		}
		avg/=durations.size() * 60;
		avgDur.setStat(avg);
		//Gets the average in minutes
		
//		print("Abritrary");
//		print("-------------------------------------------------------------------------");
//		print(arbList.toString());
//		print("-------------------------------------------------------------------------");
//		print("rangeList");
//		print("-------------------------------------------------------------------------");
//		print(rangeList.toString());
//		print("-------------------------------------------------------------------------");
//		print("hoursList");
//		print("-------------------------------------------------------------------------");
//		print(hoursList.toString());
//		print("-------------------------------------------------------------------------");
//		print("minutesList");
//		print("-------------------------------------------------------------------------");
//		print(minutesList.toString());
//		print("-------------------------------------------------------------------------");
//		print("secondsList");
//		print("-------------------------------------------------------------------------");
//		print(secondsList.toString());
//		print("-------------------------------------------------------------------------");
//		print("moreThanList");
//		print("-------------------------------------------------------------------------");
//		print(moreThanList.toString());
//		print("-------------------------------------------------------------------------");
//		print("noneList");
//		print("-------------------------------------------------------------------------");
//		print(noneList.toString());
//		print("-------------------------------------------------------------------------");
		print(avg + " minutes per incident on average");
	}
	/**
	 * Converts a String of minutes, seconds or hours into it's equivalent second mutiplier.
	 * 
	 * @param s The time unit you wanted converted
	 * @return The multiplier
	 */
	private int timeUnitToNum(String s)
	{
		return s.matches("seconds?|secs?") ? 1 : s.matches("minutes?|mins?") ? 60 : 3600;
	}
	/**
	 * Utility function used for print statements just to short typing. Can be ignored.
	 * @param arg0
	 */
	public static void print(Object arg0)
	{
		System.out.println(arg0);
	}

	/**
	 * Sets the range of years used within the youtubeSightings() method
	 * @param start The start year
	 * @param end The end year
	 */
	public void setYearRange(String start, String end)
	{
		startYear = start; endYear = end;
	}
	
	private void states()
	{
		
		stateList = new ArrayList<String>(Arrays.asList(new String[]{
			    "Alabama,Ala.,AL",
			    "Alaska,Alaska,AK",
			    "American,Samoa,,,AS",
			    "Arizona,Ariz.,AZ",
			    "Arkansas,Ark.,AR",
			    "California,Calif.,CA",
			    "Colorado,Colo.,CO",
			    "Connecticut,Conn.,CT",
			    "Delaware,Del.,DE",
			    "Dist.,of,Columbia,D.C.,DC",
			    "Florida,Fla.,FL",
			    "Georgia,Ga.,GA",
			    "Guam,Guam,GU",
			    "Hawaii,Hawaii,HI",
			    "Idaho,Idaho,ID",
			    "Illinois,Ill.,IL",
			    "Indiana,Ind.,IN",
			    "Iowa,Iowa,IA",
			    "Kansas,Kans.,KS",
			    "Kentucky,Ky.,KY",
			    "Louisiana,La.,LA",
			    "Maine,Maine,ME",
			    "Maryland,Md.,MD",
			    "Marshall,Islands,,,MH",
			    "Massachusetts,Mass.,MA",
			    "Michigan,Mich.,MI",
			    "Micronesia,,,FM",
			    "Minnesota,Minn.,MN",
			    "Mississippi,Miss.,MS",
			    "Missouri,Mo.,MO", "Montana,Mont.,MT",
			    "Nebraska,Nebr.,NE",
			    "Nevada,Nev.,NV",
			    "New Hampshire,N.H.,NH",
			    "New Jersey,N.J.,NJ",
			    "New Mexico,N.M.,NM",
			    "New York,N.Y.,NY",
			    "North Carolina,N.C.,NC",
			    "North Dakota,N.D.,ND",
			    "Northern Marianas,,MP",
			    "Ohio,Ohio,OH",
			    "Oklahoma,Okla.,OK",
			    "Oregon,Ore.,OR",
			    "Palau,,PW",
			    "Pennsylvania,Pa.,PA",
			    "Puerto,Rico,P.R.,PR",
			    "Rhode,Island,R.I.,RI",
			    "South Carolina,S.C.,SC",
			    "South Dakota,S.D.,SD",
			    "Tennessee,Tenn.,TN",
			    "Texas,Tex.,TX",
			    "Utah,Utah,UT",
			    "Vermont,Vt.,VT",
			    "Virginia,Va.,VA",
			    "Virgin Islands,V.I.,VI",
			    "Washington,Wash.,WA",
			    "West,Virginia,W.Va.,WV",
			    "Wisconsin,Wis.,WI",
			    "Wyoming,Wyo.,WY" }));
	}
	
	public void setIncidents(ArrayList<Incident> i)
	{
		incidents = i;
	}
	

	
}
