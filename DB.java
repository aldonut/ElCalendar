
import java.sql.*;
import java.util.ArrayList;

public class DB
{

	public static void insert(int year, int month, int day, 
		int startTime, int endTime, String description, String startTod, String endTod, int color)
	{
		try{

               Connection myConn = DriverManager.getConnection(
			    "jdbc:mysql://localhost:3306/calendar?autoReconnect=true&useSSL=false", "root", "chodo");

		Statement instertStmt = myConn.createStatement();

		String event = "insert into events" 
					+ "(year, month, day, startTime, endTime," + 
					" description, startTod, endTod, color)"
					+ "values(" + 
					"'" + year        + "'," +
					"'" + month       + "'," +
					"'" + day         + "'," +
					"'" + startTime   + "'," +
					"'" + endTime     + "'," +
					"'" + description + "'," +
					"'" + startTod    + "'," +
					"'" + endTod      + "'," +
					"'" + color       + "'" +
					 ")";
			
			instertStmt.executeUpdate(event);

			System.out.println("event added");
		}

		catch (Exception e) {System.out.println(e);}

	}

	public static void delete(Event event)
	{
		String deletedEvent = "";


		try{

		 Connection myConn = DriverManager.getConnection(
			    "jdbc:mysql://localhost:3306/calendar", "root", "chodo");

		 Statement myStmt = myConn.createStatement();

		 Statement delStmt = myConn.createStatement();


		 String delete = "DELETE FROM events " +
                 "WHERE year =  '" + event.getYear() + "'"
                 + " AND " + "month = '" + event.getMonth() + "'"
                 + " AND " + "day = '" + event.getDay() + "'"
                 + " AND " + "startTime = '" + event.getStartTime() + "'"
                 + " AND " + "description = '" + event.getDescription() + "'";
  					//testing
                 System.out.println(delete);
  		
		 delStmt.executeUpdate(delete);
 			}

 		catch (Exception e) {System.out.println(e);}
 		  //testing
		 System.out.println("event deleted " + deletedEvent);		 
	}


	public static ArrayList<Event> loadAll()
	{

		ArrayList<Event> allEvents = null;

		try{
				//connect
				Connection myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/calendar", "root", "chodo");
			
				//add
				Statement loadAll = myConn.createStatement();

				ResultSet myRs = loadAll.executeQuery("select * from events");
					

					allEvents = createEvents(myRs);

				
			}
			catch (Exception e) {System.out.println(e);}


				return 	allEvents;
	}

	public static ArrayList<Event> loadMonth(int year, int month)
	{
		ArrayList<Event> monthEvents = null;
		try{
		//connect
		Connection myConn = DriverManager.getConnection(
		      "jdbc:mysql://localhost:3306/calendar", "root", "chodo");

		Statement loadMonth = myConn.createStatement();

		ResultSet myRs = loadMonth.executeQuery("select * from events where year = " +
			year + " and month = " + month);


		monthEvents = createEvents(myRs);

		}

		catch (Exception e) {System.out.println(e);}


		return monthEvents;

	}

	public static ArrayList<Event> loadDay(int year, int month, int day)
	{
		ArrayList<Event> dayEvents = null;
		try{
		//connect
		Connection myConn = DriverManager.getConnection(
		      "jdbc:mysql://localhost:3306/calendar", "root", "chodo");

		Statement loadDay = myConn.createStatement();

		ResultSet myRs = loadDay.executeQuery("select * from events where year = " +
			year + " and month = " + month + " and day = " + day);


		dayEvents = createEvents(myRs);

		}

		catch (Exception e) {System.out.println(e);}

		return dayEvents;
	}

	public static ArrayList<Event> createEvents(ResultSet myRs)
	{
		Event newEvent = null;
		ArrayList<Event> eventsList = new ArrayList();

		try
		{
			while (myRs.next())
				{
					
				
		int year = Integer.parseInt(myRs.getString("year"));
		int month = Integer.parseInt(myRs.getString("month"));
		int day = Integer.parseInt(myRs.getString("day"));
		int startTime = Integer.parseInt(myRs.getString("startTime"));
		int endTime = Integer.parseInt(myRs.getString("endTime"));
		String description = myRs.getString("description");
		String startTod = myRs.getString("startTod");
		String endTod = myRs.getString("endTod");
		int color = Integer.parseInt(myRs.getString("color"));

		newEvent = new Event(year, month, day, startTime,
						endTime, description, startTod, endTod, color);

		eventsList.add(newEvent);
		}
		}
		catch (Exception e) {System.out.println(e);}


		return eventsList;
	}

	public static void update()
	{
		
	}
}