package statsPanel.model;

public class Statistic {

	private Object stat; private String name;
	private final String DEFAULT_STATISTIC = "No data";
	/**
	 * Class used to store each statistic
	 * @param name The name/title of the statistic
	 * @param stat The actual statistic
	 */
	public Statistic(String name, Object stat)
	{
		this(name); this.stat = stat;
	}
	/**
	 * A default constructor for statistics before they get their raw data
	 * @param name
	 */
	public Statistic(String name)
	{
		this.name = name; stat = DEFAULT_STATISTIC;
	}
	
	public Object getStat()
	{
		return stat;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setStat(Object o)
	{
		stat = o;
	}
	
	/**
	 * Returns the string representation of the statistic; the title and the raw statistic
	 */
	public String toString()
	{
		return name + "\n" + String.valueOf(stat);
	}
}
