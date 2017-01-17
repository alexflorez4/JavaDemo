import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;


public class NBATeam implements Team {

	private int teamID = 0;
	private String teamName = null;
	private String teamCity = null;
	private int teamRank = 0;
	
	public NBATeam(int int1, String string, String string2, int int2) 
	{
		this.teamID = int1;
		this.teamName = string;
		this.teamCity = string2;
		this.teamRank = int2;
	}
	
	public String toString()  //String method overloaded
	{
		return String.format("%4d%15s%15s%15d",getTeamID(),getTeamName(),getTeamCity(),getTeamRank());
	}

	public NBATeam() //default constructor
	{
	
	}
	
	NBATeam(NBATeam c) //copy constructor
	{
		
	}

	//setters and getters
	
	private int getTeamID() {
		return teamID;
	}

	private void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	private String getTeamName() {
		return teamName;
	}

	private void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	private String getTeamCity() {
		return teamCity;
	}

	private void setTeamCity(String teamCity) {
		this.teamCity = teamCity;
	}

	private int getTeamRank() {
		return teamRank;
	}

	private void setTeamRank(int teamRank) {
		this.teamRank = teamRank;
	}

	@Override
	public void getName() 
	{
	}

	@Override
	public void getCity() 
	{
	}
	
	public void printNumberOfTeams(Connection connection)
	{
		try 
		{
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery("SELECT count(team_id) from TEAM");
			
			while (resultset.next())
			{
				String team = resultset.getString(1);
				System.out.println("\nNumber of teams: " + team);
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
	public int add(Connection connection, String name, String city) 
	{
		try 
		{
			CallableStatement callablestatement = connection.prepareCall("{call ? := TEAM_pkg.add_team(?,?)}");
			callablestatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestatement.setString(2, name);
			callablestatement.setString(3, city);
			ResultSet result = callablestatement.executeQuery();
			int retValue = callablestatement.getInt(1);
			System.out.println("\nNew team ID: " + retValue + "\n" );
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
	public void updateCity(Connection connection, String name, String city) 
	{
		try 
		{
			CallableStatement callablestatement = connection.prepareCall("{call ? := TEAM_pkg.updatecity(?,?)}");
			callablestatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestatement.setString(2, name);
			callablestatement.setString(3, city);
			ResultSet result = callablestatement.executeQuery();
			int retValue = callablestatement.getInt(1);
			//callablestatement.close();
			result.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void updateName(Connection connection, String oldName, String newName) 
	{
		try 
		{
			CallableStatement callablestatement = connection.prepareCall("{call ? := TEAM_pkg.updatename(?,?)}");
			callablestatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestatement.setString(2, oldName);
			callablestatement.setString(3, newName);
			ResultSet result = callablestatement.executeQuery();
			int retValue = callablestatement.getInt(1);
			callablestatement.close();
			result.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void updateChampionships(Connection connection, String name)
	{
		try 
		{
			CallableStatement callablestatement = connection.prepareCall("{call ? := TEAM_pkg.updateChampionships(?)}");
			callablestatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestatement.setString(2, name);
			ResultSet result = callablestatement.executeQuery();
			int retValue = callablestatement.getInt(1);
			callablestatement.close();
			result.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void remove(Connection connection, int teamID) 
	{
		try 
		{
			CallableStatement callablestatement = connection.prepareCall("{call ? := TEAM_pkg.remove(?)}");
			callablestatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callablestatement.setInt(2, teamID);
			ResultSet result = callablestatement.executeQuery();
			int retValue = callablestatement.getInt(1);
			callablestatement.close();
			result.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}

}
