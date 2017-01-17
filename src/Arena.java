import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


public abstract class Arena 
{
	static void printTable(Connection connection)
	{
		try 
		{
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery("SELECT arena_id, aname, city from Arena");
			
			System.out.println("Arena ID" + "\t" + "Arena name" + "\t" + "Arena city");
			System.out.println("------------------------------------------------------");

			while (resultset.next())
			{
				int arenaid = resultset.getInt(1);				
				String arenaname = resultset.getString(2);
				String arenacity = resultset.getString(3); 
				System.out.format("%2d%25s%15s", arenaid, arenaname, arenacity);
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

	abstract void printNumberOfArenas(Connection connection);
	
	abstract int add(Connection connection, String string, String string2);

	abstract void remove(Connection connection, String arenaname);
	
	abstract void remove(Connection connection, int arenaid);

	static Collection<NBAArena> getCollection(Connection connection) 
	{
		try 
		{
			ArrayList<NBAArena> arenalist = new ArrayList<NBAArena>();
			Statement st = connection.createStatement();
			ResultSet srs = st.executeQuery("SELECT ARENA_ID, ANAME, CITY  FROM Arena");
			while(srs.next())
			{
				//System.out.print("\n" + srs.getInt("TEAM_ID"));
				NBAArena arena = new NBAArena(srs.getInt("ARENA_ID"), srs.getString("ANAME"), srs.getString("CITY"));
				arenalist.add(arena);
			}
			st.close();
			srs.close();
			return arenalist;
		} 
		catch (SQLException e) {
		
			e.printStackTrace();
		}
		return null;
	}

	abstract void printCollection(Collection<NBAArena> arenaColl);

}
