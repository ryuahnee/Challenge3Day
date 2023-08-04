package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

	public static Connection getConnection() throws SQLException {
		//jdbc:apache:commons:dbcp:커넥션풀이름
		//xml문서에서 poolName=challenge3 하기!!!!
		return DriverManager.getConnection("jdbc:apache:commons:dbcp:challenge3");
	}
}
