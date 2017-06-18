import java.sql.*;

/**
*this class is a test to see how I can access 
*the db usinng java
*/

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try 
		{
			Connection myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sonoo", "root", "chodo");
			Statement myStmt = myConn.createStatement();
			
			ResultSet myRs = myStmt.executeQuery("select * from test");
			while (myRs.next())
			{
				System.out.println(myRs.getString("id") + ", " + 
			     myRs.getString("first") + ", " + myRs.getString("last"));
			}
		}
		catch (Exception e) {System.out.println(e);
	}

}
}

