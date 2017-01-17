import java.sql.Connection;
import java.util.Collection;

public interface Player 
{
	void printTable(Connection connection);

	void printNumberOfPlayers(Connection connection);

	int add(Connection connection, String playername, String playerposition, int playerdraftyear,
			String playereducation);

	void retire(Connection connection, String playername, int draftyear);

	void retire(Connection connection, int playerID, int draftyear);

	java.util.Collection getCollection(Connection connection);

	void printCollection(Collection playerColl);

}
