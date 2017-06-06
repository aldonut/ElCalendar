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
	private String tod;

	
	
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
	public Event(String year, String month, String day, String startTime, 
		String endTime, String description, String tod)
	{
		this.year = Integer.parseInt(year);
		this.month = Integer.parseInt(month);
		this.day = Integer.parseInt(day);
		this.description = description;
		this.startTime = Integer.parseInt(startTime);
		this.endTime = Integer.parseInt(endTime);
		this.tod = tod;
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
		
		if(this.getTod().equals(other.getTod()))
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
			return this.getTod().compareTo(o.getTod());
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
	
    public String getTod()
    {
    	return tod;
    }

    public void setTod(String tod)
    {
    	this.tod = tod;
    }

    public String getStrDate()
    {
    	return month + "/" + day + "/" + year;
    }
	/**
	 * to string rep of the event
	 */
	public String toString()
	{
		return description + " " +  startTime + " - "  + endTime;
	}

	
}
