import java.text.ParseException;
import java.util.Scanner;

/**
 * 
 * @author aldonut the class creates event obj
 * with all info about event
 *
 */
public class Event implements Comparable<Event>
{
	private int startTime;
	private int endTime;
	private String description;
	private String strDate;
	private int year;
	private int month;
	private int day;
	private String startTod;
	private String endTod;

	
	
	/**
	 * 
	 * @param year event year
 	 * @param month event month
 	 * @param day event day 
 	 * @param startTime the start time
 	 * @param endTime event end time
	 * @param description event description
	 * @param tod the time of day 
	 */
	// I deleted the tod variable and changed year type, month type and day type from String to int (Richard)
<<<<<<< HEAD
	public Event(int year, int month, int day, int startTime, int endTime, String description, String startTod, String endTod)
=======
	public Event(int year, int month, int day, int startTime, 
		int endTime, String description, String startTod, String endTod)
>>>>>>> 9c90cbbacd196fbe9ece9d1b1e2474d4605b9052
	{
		this.year = year;
		this.month = month;
		this.day = day;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
<<<<<<< HEAD
		this.startTod = startTod;
		this.endTod = endTod;
=======
>>>>>>> 9c90cbbacd196fbe9ece9d1b1e2474d4605b9052
	}
	

    /**
	 * compares events by start time 
	 * if same then compares by end time
	 * if same then compares alphabetically 
	 * @param o the other event 
	 */
	public int compareTo(Event o)
	{
		Event other = (Event)o;
		
		if(this.getStartTod().equals(other.getStartTod()))
		{
			if(this.getStartTime() == other.getStartTime())
			{
				if(this.getEndTime() == other.getEndTime())
				{
					return this.getDescription().compareTo(other.getDescription());
				}

				else
				{
					return timeCompare(this.getEndTime(), o.getEndTime());
				}
			}

			else
			{
				return timeCompare(this.getStartTime(), o.getStartTime());
			}
		}

		else
		{
			return this.getStartTod().compareTo(o.getStartTod());
		}
		
	}
	

	public int timeCompare(int thisTime, int otherTime)
	{
		if(thisTime - 1200 >= 0)
			thisTime -= 1200;
		if(otherTime - 1200 >= 0)
			otherTime -= 1200;

		return thisTime - otherTime;
	}

	public void allDay()
	{
		startTime = 1200;
		endTime = 1145;
		startTod = "am";
		endTod = "pm";
	}
	/**
	 * 
	 * @return string rep of start time 
	 */
	public int getStartTime()
	{
		return startTime;
	}

	public void setStartTime(int time)
	{
		startTime = time;
	}
	
	/**
	 * 
	 * @return String rep of end time 
	 */
	public int getEndTime()
	{
		return endTime;
	}

	public void setEndTime(int time)
	{
		endTime = time;
	}
	
	/**
	 * 
	 * @return string rep of description
	 */
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String text)
	{
		description = text;
	}
	
    public String getStartTod()
    {
    	return startTod;
    }

    public void setStartTod(String tod)
    {
    	this.startTod = tod;
    }

    public String getendTod()
    {
    	return endTod;
    }

    public void setendTod(String tod)
    {
    	this.endTod = tod;
    }

    public String getStrDate()
    {
    	String strMonth = String.valueOf(month);
    	String strDay = String.valueOf(day);
    	if(month < 9)
    		strMonth = "0" + strMonth;
    	if(day < 9)
    		strDay = "0" + strDay;
    	return strMonth + "/" + strDay + "/" + year;
    }
	/**
	 * to string rep of the event
	 */
	public String toString()
	{
		String startHour = String.valueOf(startTime/100);
		String startMin = String.valueOf(startTime % 100);
		if(startMin.equals("0"))
			startMin = "00";
		String strStartTime = startHour  + ":" + startMin + " " + startTod;
		
		String endHour = String.valueOf(endTime/100);
		String endMin = String.valueOf(endTime % 100);
		if(endMin.equals("0"))
			endMin = "00";
		String strEndTime = endHour + ":" + endMin + " " + endTod;
		return description + "   " +  strStartTime + "  -  " + strEndTime;
	}

	
}

