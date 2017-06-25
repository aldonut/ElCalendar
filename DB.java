import java.sql.*;

public class DB
{
	public static void insert(int year, int month, int day, 
		int startTime, int endTime, String description, String startTod, String endTod)
	{

		try{


               Connection myConn = DriverManager.getConnection(
			    "jdbc:mysql://localhost:3306/calendar", "root", "chodo");

		Statement instertStmt = myConn.createStatement();

		System.out.println(startTod);

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
					"'" + endTod + "'" +


					 ")";
			
			instertStmt.executeUpdate(event);

			System.out.println("event added");
		}

		catch (Exception e) {System.out.println(e);}

	}

}