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


public class NBAPlayer implements Player 
{
	private int PLAYER_ID = 0;
	private String PNAME = null;
	private String POSITION = null;
	private int DRAFT_YEAR = 0;
	private int RETIRE_YEAR = 0;
	private String EDUCATION = null;
	
	private int getPLAYER_ID() 
	{
		return PLAYER_ID;
	}

	private void setPLAYER_ID(int pLAYER_ID) 
	{
		PLAYER_ID = pLAYER_ID;
	}

	private String getPNAME() 
	{
		return PNAME;
	}

	private void setPNAME(String pNAME) 
	{
		PNAME = pNAME;
	}

	private String getPOSITION() 
	{
		return POSITION;
	}

	private void setPOSITION(String pOSITION) 
	{
		POSITION = pOSITION;
	}

	private int getDRAFT_YEAR() 
	{
		return DRAFT_YEAR;
	}

	private void setDRAFT_YEAR(int dRAFT_YEAR) 
	{
		DRAFT_YEAR = dRAFT_YEAR;
	}

	private int getRETIRE_YEAR() 
	{
		return RETIRE_YEAR;
	}

	private void setRETIRE_YEAR(int rETIRE_YEAR) 
	{
		RETIRE_YEAR = rETIRE_YEAR;
	}

	private String getEDUCATION() 
	{
		return EDUCATION;
	}

	private void setEDUCATION(String eDUCATION) 
	{
		EDUCATION = eDUCATION;
	}

	public String toString()
	{
		return String.format("%2d%25s%15s%14d%16d%20s", getPLAYER_ID(), getPNAME(), getPOSITION(), getDRAFT_YEAR(),
				getRETIRE_YEAR(), getEDUCATION());
	}
	
	public NBAPlayer(int pid, String pname, String ppos, int pdraft,
			int pretire, String peducation) 
	{
		this.PLAYER_ID = pid;
		this.PNAME = pname;
		this.POSITION = ppos;
		this.DRAFT_YEAR = pdraft;
		this.RETIRE_YEAR = pretire;
		this.EDUCATION = peducation;				
	}

	public NBAPlayer()  //default constructor
	{
		
	}

	NBAPlayer(NBAPlayer c) //copy constructor
	{
		
	}
	
	@Override
	public void printTable(Connection connection) 
	{
		try 
		{
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery("SELECT player_id, pname, position, draft_year, retire_year, education from player");
			
			System.out.println("Player ID" + "\t" + "Player name" + "\t" + "Position" + "\t" + "Draft Year" + "\t" + "Retire Year" + "\t" + "Education");
			System.out.println("---------------------------------------------------------------------------------");

			while (resultset.next())
			{
				int playerid = resultset.getInt(1);				
				String playername = resultset.getString(2);
				String position = resultset.getString(3); 
				int draftyear = resultset.getInt(4);
				int retireyear = resultset.getInt(5);
				String education = resultset.getString(5); 
				System.out.format("%2d%25s%15s%14d%16d%15s", playerid, playername, position, draftyear, retireyear, education);
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
	
	@Override
	public void printNumberOfPlayers(Connection connection) 
	{
		try 
		{
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery("SELECT count(player_id) from PLAYER");
			
			while (resultset.next())
			{
				String player = resultset.getString(1);
				System.out.println("\nNumber of Players: " + player);
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
	
	@Override
	public int add(Connection connection, String playername,
			String playerposition, int playerdraftyear, String playereducation) 
	{
		try 
		{
			CallableStatement callablestatement = connection.prepareCall("{call ? := PLAYER_pkg.add(?,?,?,?)}");
			callablestatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestatement.setString(2, playername);
			callablestatement.setString(3, playerposition);
			callablestatement.setInt(4, playerdraftyear);
			callablestatement.setString(5, playereducation);
			ResultSet result = callablestatement.executeQuery();
			int retValue = callablestatement.getInt(1);
			System.out.println("\nNew player ID: " + retValue + "\n" );
			result.close();
			callablestatement.close();
			return retValue;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
		return 0;
	}

	@Override
	public void retire(Connection connection, String playername, int draftyear) 
	{
		try 
		{
			CallableStatement callablestatement = connection.prepareCall("{call ? := PLAYER_pkg.retire(?,?)}");
			callablestatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestatement.setString(2, playername);
			callablestatement.setInt(3, draftyear);
			ResultSet result = callablestatement.executeQuery();
			int retValue = callablestatement.getInt(1);
			System.out.println("\nPlayer ID retire updated: " + retValue + "\n" );
			callablestatement.close();
			result.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
	}

	@Override
	public void retire(Connection connection, int playerID, int draftyear) 
	{
		try 
		{
			CallableStatement callablestatement = connection.prepareCall("{call ? := PLAYER_pkg.retire(?,?)}");
			callablestatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestatement.setInt(2, playerID);
			callablestatement.setInt(3, draftyear);
			ResultSet result = callablestatement.executeQuery();
			int retValue = callablestatement.getInt(1);
			System.out.println("\nPlayer ID retire updated: " + retValue + "\n" );
			callablestatement.close();
			result.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
		
	}
	
	@Override
	public Collection getCollection(Connection connection) 
	{
		try 
		{
			ArrayList<NBAPlayer> playerlist = new ArrayList<NBAPlayer>();
			Statement st = connection.createStatement();
			ResultSet srs = st.executeQuery("SELECT PLAYER_ID, PNAME, POSITION, DRAFT_YEAR, RETIRE_YEAR, EDUCATION FROM PLAYER");
			while(srs.next())
			{
				NBAPlayer player = new NBAPlayer(srs.getInt("PLAYER_ID"), srs.getString("PNAME"), srs.getString("POSITION"), srs.getInt("DRAFT_YEAR"), srs.getInt("RETIRE_YEAR"), srs.getString("EDUCATION"));
				playerlist.add(player);
			}
			st.close();
			srs.close();
			return playerlist;
		} 
		catch (SQLException e) {
		
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void printCollection(Collection playerColl) 
	{
		System.out.println("Player ID" + "\t" + "Player name" + "\t" + "Position" + "\t" + "Draft year"
						+ "\t" + "Retire year" + "\t" + "Education");
		System.out.println("---------------------------------------------------------------------------------------------");
		if (playerColl instanceof Map)
		{
			Map theMap = (Map) playerColl;
			Set keys=theMap.keySet();
			Iterator keyIterator = keys.iterator();
			while(keyIterator.hasNext())
			{
				System.out.println(theMap.get(keyIterator.next()).toString());
			}
 		}
		else
		{
			ArrayList<NBAPlayer> playerlist = new ArrayList<NBAPlayer>();
			Iterator iterator = playerColl.iterator();
			while(iterator.hasNext())
			{
				System.out.println(iterator.next().toString());
			}
			
		}		
		
	}

}
