import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class NBAArena extends Arena 
{
	private int ARENAID = 0;
	private String ARENANAME = null;
	private String ARENACITY = null;
	
	private int getARENAID() {
		return ARENAID;
	}

	private void setARENAID(int aRENAID) {
		ARENAID = aRENAID;
	}

	private String getARENANAME() {
		return ARENANAME;
	}

	private void setARENANAME(String aRENANAME) {
		ARENANAME = aRENANAME;
	}

	private String getARENACITY() {
		return ARENACITY;
	}

	private void setARENACITY(String aRENACITY) {
		ARENACITY = aRENACITY;
	}

	public NBAArena(int ARENA_ID, String ARENA_NAME, String ARENA_CITY) 
	{
		this.ARENAID = ARENA_ID;
		this.ARENANAME = ARENA_NAME;
		this.ARENACITY = ARENA_CITY;
	}

	public NBAArena()  //default constructor
	{
		
	}
	
	NBAArena(NBAArena c) //copy constructor
	{
	
	}
	
	public String toString()
	{
		return String.format("%2d%25s%15s", getARENAID(), getARENANAME(), getARENACITY());
	}
	
	@Override
	void printNumberOfArenas(Connection connection) 
	{
		try 
		{
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery("SELECT count(arena_id) from ARENA");
			
			while (resultset.next())
			{
				String arena = resultset.getString(1);
				System.out.println("\nNumber of Arenas: " + arena);
			}
			
			resultset.close();
			//connection.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	int add(Connection connection, String arenaname, String arenacity) 
	{
		try 
		{
			CallableStatement callablestatement = connection.prepareCall("{call ? := ARENA_pkg.add(?,?)}");
			callablestatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestatement.setString(2, arenaname);
			callablestatement.setString(3, arenacity);
			ResultSet result = callablestatement.executeQuery();
			int retValue = callablestatement.getInt(1);
			System.out.println("\nNew Arena ID: " + retValue + "\n" );
			callablestatement.close();
			result.close();
			return retValue;
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
		return 0;
	}

	@Override
	void remove(Connection connection, String arenaname) 
	{
		try 
		{
			CallableStatement callablestatement = connection.prepareCall("{call ? := ARENA_pkg.remove(?)}");
			callablestatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestatement.setString(2, arenaname);
			ResultSet result = callablestatement.executeQuery();
			int retValue = callablestatement.getInt(1);
			System.out.println("\nArena ID removed: " + retValue + "\n" );
			callablestatement.close();
			result.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}			
	}

	@Override
	void remove(Connection connection, int arenaid) 
	{
		try 
		{
			CallableStatement callablestatement = connection.prepareCall("{call ? := ARENA_pkg.remove(?)}");
			callablestatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestatement.setInt(2, arenaid);
			ResultSet result = callablestatement.executeQuery();
			int retValue = callablestatement.getInt(1);
			System.out.println("\nNew Arena ID: " + retValue + "\n" );
			callablestatement.close();
			result.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
		
	}

	@Override
	void printCollection(Collection<NBAArena> arenaColl) 
	{
		System.out.println("Arena ID" + "\t" + "Arena name" + "\t" + "Arena city");
		System.out.println("------------------------------------------------------------------------");
		if (arenaColl instanceof Map)
		{
			Map theMap = (Map) arenaColl;
			Set keys=theMap.keySet();
			Iterator keyIterator = keys.iterator();
			while(keyIterator.hasNext())
			{
				System.out.println(theMap.get(keyIterator.next()).toString());
			}
 		}
		else
		{
			ArrayList<NBAArena> arenalist = new ArrayList<NBAArena>();
			Iterator iterator = arenaColl.iterator();
			while(iterator.hasNext())
			{
				System.out.println(iterator.next().toString());
			}
		}		
	}
}
