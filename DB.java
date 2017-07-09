import java.sql.*;

public class DB
{
	public static int idCount = 5; 

	public static void insert(int year, int month, int day, 
		int startTime, int endTime, String description, String startTod, String endTod)
	{

		try{

               Connection myConn = DriverManager.getConnection(
			    "jdbc:mysql://localhost:3306/calendar", "root", "chodo");

		Statement instertStmt = myConn.createStatement();

		String event = "insert into events" 
					+ "(year, month, day, startTime, endTime," + 
					" description, startTod, endTod)"
					+ "values(" + 
					"'" + year + "'," +
					"'" + month + "'," +
					"'" + day + "'," +
					"'" + startTime + "'," +
					"'" + endTime + "'," +
					"'" + description + "'," +
					"'" + startTod + "'," +
					"'" + endTod + "'," +


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

	public static void load()
	{
		
	}
}