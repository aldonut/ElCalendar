import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Model
{
	private Calendar movedAroundCal = Calendar.getInstance();
	private Calendar localCal = Calendar.getInstance();
	private int dayCount = 0;
	private ArrayList<Day> usedDays;
	private Day chosenDay;
	private int[][] cellDays;
	private ArrayList<ChangeListener> listeners;
	
	public Model()
	{
		
		usedDays = new ArrayList<>();
		cellDays = new int[6][7];
		listeners = new ArrayList();
	}

	public int firstDay()
	{	
		Calendar cal = Calendar.getInstance();
		cal = (Calendar) movedAroundCal.clone();
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    cal.get(Calendar.DAY_OF_WEEK);
		  return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * calls method within month is order 
	 * to go to the next month 
	 */
	public void nextMonth()
	{
		movedAroundCal.add(Calendar.MONTH, 1);
		setToday();
		changed();
	}

	/**
	 * 
	 * @return returns the first day of the month as an int
	 */
	
	
	/**
	 * moves instance of month to the prev one
	 */
	public void prevMonth()
	{
	    movedAroundCal.add(Calendar.MONTH, -1);
		setToday();
		changed();
	}
	
	/**
	 * goes to the next day 
	 * and then takes you back 
	 * to the view 
	 */
	public void nextDay()
	{
		movedAroundCal.add(Calendar.DAY_OF_YEAR, 1);
		setToday();
		changed();
	}
	
	/**
	 * goes to the prev day 
	 * and then takes you back 
	 * to the view 
	 */
	public void prevDay()
	{
		
		movedAroundCal.add(Calendar.DAY_OF_YEAR, -1);
		setToday();
		changed();
	}
	
	public void setChosenDay(int x, int y)
	{
		
		int chosenDay = cellDays[x][y];
		movedAroundCal.set(Calendar.DAY_OF_MONTH,chosenDay);
		Date todayDate = movedAroundCal.getTime();
		String strToday = dateFormatter(todayDate);
		if(findDay(strToday) == null)
		{
			Day today = new Day(strToday);
			this.chosenDay = today;
		}
		else
		{
			this.chosenDay = findDay(strToday);

		}
		
		
		changed();
	}

	
	public void setToday()
	{
		 Date todayDate = movedAroundCal.getTime();
		 String strToday = dateFormatter(todayDate);
		 
		if(findDay(strToday) == null)
		{
			Day today = new Day(strToday);
			this.chosenDay = today;
		}
		else
		{
			this.chosenDay = findDay(strToday);
		}
		
		changed();

			
			
	}
	
	public String dateFormatter(Date d)
	{
	     DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	     String day = df.format(d);
	     return day;

	}
	
	/**
	 * iterates through sorted days and then 
	 * prints the events for those days
	 */
	public void allEvents()
	{
		Collections.sort(usedDays);
		
		for(int i = 0; i < dayCount; i++)
		{
			usedDays.get(i).returnEventList();
			System.out.println("Day: " + usedDays.get(i).getDay());
			System.out.println("Month: " + usedDays.get(i).getMonth());
			System.out.println("Year: " + usedDays.get(i).getYear());

		}
	}
	
	/**
	 * 
	 * @param in the scanner being used 
	 * @throws ParseException throws parseExpection
	 * @throws IOException throws parseExceptions
	 */
	
	public void addEvent(int year, int month, int day, int startTime, int endTime, String description, String startTod, String endTod)
	{
		Event eventToAdd = new Event(year, month, day, startTime, endTime, description, startTod, endTod);
		String eventDate = eventToAdd.getStrDate();
		Day eventDay = null;
		if(dayAlreadyExists(eventDate))
		{
		    eventDay = findDay(eventDate);
			eventDay.addEvent(eventToAdd);
		}
		else
		{
			eventDay = new Day(eventDate);
			eventDay.addEvent(eventToAdd);
//			addNewDay(eventDay);           (I think it adds twice becase of this line.)
		}
		
		usedDays.add(eventDay);
		
		changed();
	}
	
	
	/**
	 * 
	 * @param in  the scanner being used 
	 * deletes either a certain day's events 
	 * all the days 
	 */
	public void delete(String input)
	{
		
			String date = input;
			
			for(int i = 0; i < dayCount; i++)
			{
				if(usedDays.get(i).getDate().equals(date))
				{
					usedDays.remove(i);
					dayCount--;
				}
			}
			
	}
		
		
		
	
	
	/**
	 * 
	 * @param eventDate the date you're looking for 
	 * @return the day or null if it doesn't exist
	 */
	public Day findDay(String eventDate)
	{
		for(Day current: usedDays)
		{
			if(current.getDate().equals(eventDate))
			{
				return current;
			}
		}
		
		return null;
	}
	
	
	/**
	 * 
	 * @param day the day you want to add 
	 * to arraylist 
	 */
	public void addNewDay(Day day)
	{
		usedDays.add(day);
		dayCount++;
	}
	
	/**
	 * 
	 * @param day the day you're looking for 
	 * @return whether the day exists of not 
	 */
	public boolean dayAlreadyExists(String day)
	{
		
		for(int i = 0; i < dayCount; i++) //wtf is wrong with this. The loops doesn't run. I'm testing delete
		{
			
			if(usedDays.get(i).getDate().equals(day))
			return true;
		}
		return false;
	}
	
	public ArrayList<Day> getDaysArr()
	{
		return usedDays;
	}
	
	public Calendar getMovedAroundCal()
	{
		return movedAroundCal;
	}
	
	public Calendar getLocalCal()
	{
		return localCal;
	}
	
	
	public Day getChosenDay()
	{
		return chosenDay;
	}
	
	public void addChangeListener(ChangeListener listener)
	   {    listeners.add(listener); }
	
	public void changed()
	{
	      // Notify all observers of the change to the invoice
	      ChangeEvent event = new ChangeEvent(this);
	      for (ChangeListener listener : listeners)
	         listener.stateChanged(event);
	}
	
	public void setCellDays(int x, int y, int day)
	{
		cellDays[x][y] = day;
	}

	
}

