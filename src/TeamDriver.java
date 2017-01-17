
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TeamDriver 
{
	public static void main(String[] args) throws SQLException {
				
		String username = "aflorez2012";
		String password = "1037574336";
		
		// get a connection to DB
		Connection connection = getConnection(username , password); //1.a
		
		//print the table
		printTeamTable(connection); //1.b
				
		//declare variables
		Team team = new NBATeam( ); //1.c
				
		//modify some data
		System.out.println("Before add ..");
		team.printNumberOfTeams(connection); //1.d
		int teamID_jazz = team.add(connection, "Jazz", "Utah"); //1.e
		@SuppressWarnings("unused")
		int teamID_raptors = team.add(connection, "Raptors", "Toronto"); //1.e
		System.out.println("After add ..");
		team.printNumberOfTeams(connection);
						
		team.updateCity(connection, "Clippers", "Los Angeles"); //1.e
		team.updateName(connection, "Blazers", "Trail Blazers"); //1.e
		team.updateChampionships(connection, "Mavericks"); //1.e
		System.out.println("After update ..");
		
		
		//printTeamTable(connection);		
		team.remove(connection, teamID_jazz); //1.e
		
		//print the updated tables
		System.out.println("After remove ..");
		Collection TeamColl = getTeamCollectionArrayList(connection);
		printTeamCollection (TeamColl);
				
	}

	private static void printTeamCollection(Collection teamColl) 
	{
		System.out.println("Team ID" + "\t" + "Team name" + "\t" + "Team city" + "\t" + "Team Rank");
		System.out.println("------------------------------------------------------------------------");
		if (teamColl instanceof Map)
		{
			Map theMap = (Map) teamColl;
			Set keys=theMap.keySet();
			Iterator keyIterator = keys.iterator();
			while(keyIterator.hasNext())
			{
				System.out.println(theMap.get(keyIterator.next()).toString());
			}
 		}
		else
		{
			ArrayList<NBATeam> teamlist = new ArrayList<NBATeam>();
			Iterator iterator = teamColl.iterator();
			while(iterator.hasNext())
			{
				System.out.println(iterator.next().toString());
			}
		}		
	}

	private static Collection getTeamCollectionArrayList(Connection connection) {
		
		try 
		{
			ArrayList<NBATeam> teamlist = new ArrayList<NBATeam>();
			Statement st = connection.createStatement();
			ResultSet srs = st.executeQuery("SELECT TEAM_ID, TNAME, RANK, CITY  FROM Team");
			while(srs.next())
			{
				NBATeam team = new NBATeam(srs.getInt("TEAM_ID"), srs.getString("TNAME"), srs.getString("CITY"), srs.getInt("RANK"));
				teamlist.add(team);
			}
			st.close();
			srs.close();
			return teamlist;
		} 
		catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		return null;
	}

	private static void printTeamTable(Connection connection) 
	{
		try 
		{
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery("SELECT team_id, tname, rank, city, championships  from TEAM");
			
			System.out.println("Team ID" + "\t" + "Team name" + "\t" + "Team city" + "\t" + "Team rank" + "\t" + "Team Championships");
			System.out.println("------------------------------------------------------------------------");

			while (resultset.next())
			{
				int teamid = resultset.getInt(1);				
				String teamname = resultset.getString(2);
				int teamrank = resultset.getInt(3); 
				String teamcity= resultset.getString(4);
				int teamchamps = resultset.getInt(5);
				System.out.format("%4d%15s%15s%10d%15d", teamid, teamname, teamcity, teamrank, teamchamps);
				System.out.print("\n");
			}
			resultset.close();
			statement.close();
			//connection.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
	}

	private static Connection getConnection(String username, String password) 
	{		
		try 
		{
			System.out.println("\nGreeting a connection to Database.");
			Connection connection = null;
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@131.91.168.91:1521:r11g", username,password);

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
