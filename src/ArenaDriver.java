import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ArenaDriver {

	//class variables
	private static java.util.Collection<NBAArena> arenaColl;
	
	public static void main(String[] args) 
	{
		// get a connection to DB
		Connection connection = getConnection( );
		
		//declare local variable
		Arena arena = new NBAArena(); // 3.b
		
		//print the table
		Arena.printTable(connection); // 3.c
		
		//modify some data
		arena.printNumberOfArenas(connection); // 3.d
		int arenaID_oracle = arena.add(connection, "Oracle Arena", "Oakland"); // 3.e
		int arenaID_pepsi = arena.add(connection, "Pepsi Center", "Denver"); // 3.e
		int arenaID_toyota = arena.add(connection, "Toyota Center", "Houston"); //3.e
		arena.printNumberOfArenas(connection);
		Arena.printTable(connection);
		arena.remove(connection, "Oracle Arena"); // 3.e
		arena.remove(connection, arenaID_pepsi); // 3.e
		arena.remove(connection, arenaID_toyota); // 3.e
		
		//print the updated tables
		arenaColl = Arena.getCollection(connection); // 3.f
		arena.printCollection(arenaColl); // 3.f*/
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
