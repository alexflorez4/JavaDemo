import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;


public class PlayerDriver {

	//class variables
	private static Collection playerColl;
	
	public static void main(String[] args) 
	{
		// get a connection to DB
		Connection connection = getConnection( );
		
		//declare local variables
		Player player = new NBAPlayer(); //2.b
		
		//print the table
		player.printTable(connection); //2.c
		
		//modify some data
		player.printNumberOfPlayers(connection); //2.d
		int playerID_hill =
		player.add(connection, "Grant Hill", "Small forward", 1994, "Duke"); //2.e
		int playerID_nash =
		player.add(connection,"Steve Nash","Point guard",1996,"Santa Clara");//2.e
		player.printNumberOfPlayers(connection);
		player.printTable(connection);
		
		
		player.retire(connection, "Grant Hill", 2013); //2.e
		player.retire(connection, playerID_nash, 2015); //2.e*/
		
		//print the updated tables
		playerColl = player.getCollection(connection); //2.f
		player.printCollection(playerColl); //2.f
	}

	private static Connection getConnection() 
	{
		try 
		{
			System.out.println("\nGreeting a connection to Database.");
			Connection connection = null;
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@131.91.168.91:1521:r11g", "aflorez2012","1037574336");

			if (connection == null) 
			{
				System.out.println("\n\nError: failed to obtain a connection to the database, connection is null. Exiting..");
				System.exit(1);
			} 
			else 
			{
				System.out.println("\nSuccessfully obtaining a connection to the database...");
				//connection.close();
				return connection;
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Exception sql");
			e.printStackTrace();
		}		
		return null;
	}

}
