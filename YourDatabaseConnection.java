package shoppingcart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class YourDatabaseConnection {
	static {
		try {
			// Load the PostgreSQL JDBC driver
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// Handle the ClassNotFoundException appropriately
		}
	}

	// JDBC URL, username, and password of PostgreSQL server
	private static final String JDBC_URL = "jdbc:postgresql://192.168.110.48:5432/plf_training";
	private static final String USERNAME = "plf_training_admin";
	private static final String PASSWORD = "pff123";

	// Method to establish a connection to the database
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
	}
}
