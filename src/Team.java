import java.sql.Connection;

public interface Team 
{
	void getName();
	
	void getCity();
	
	void printNumberOfTeams(Connection connection);
	
	int add(Connection connection, String name, String city);
	
	void updateCity(Connection connection, String name, String city);
	
	void updateName(Connection connection, String oldName, String newName);
	
	void updateChampionships(Connection connection, String name);
	
	void remove(Connection connection, int teamID);
}
