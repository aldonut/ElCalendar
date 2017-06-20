import java.sql.*;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
		
		
		try 
		{
			//connect
			Connection myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sonoo", "root", "chodo");
			/*
			//add
			Statement myStmt2 = myConn.createStatement();
			
			String insert = "insert into test" 
					+ "(id, first, last)"
					+ "values('33333', 'hozay', 'nut')";
			
			myStmt2.executeUpdate(insert);
			//add
			*/
	//-----------------------------------------------------------------------//
			/*
			//edit
			Statement myStmt3 = myConn.createStatement();
			
			String edit = "update test " +
							" set last='longdick'"
							+ " where first='tom'";
			
			myStmt3.executeUpdate(edit);
			*/
			
			//print
			Statement myStmt = myConn.createStatement();
			
			ResultSet myRs = myStmt.executeQuery("select * from test");
			while (myRs.next())
			{
				System.out.println(myRs.getString("id") + ", " + 
			     myRs.getString("first") + ", " + myRs.getString("last"));
			}
			//print
		}
		catch (Exception e) {System.out.println(e);
	}

}
}
