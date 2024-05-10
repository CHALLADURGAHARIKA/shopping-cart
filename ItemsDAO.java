package shoppingcart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemsDAO {

	private String jdbcURL = "jdbc:postgresql://192.168.110.48:5432/plf_training";
	private String jdbcUsername = "plf_training_admin";
	private String jdbcPassword = "pff123";

	private static final String SELECT_ALL_ITEMS = "SELECT product_id, product_name, COUNT(*) AS count FROM carts181 GROUP BY product_id, product_name";

	public List<Item> getAllItems() {
		List<Item> items = new ArrayList<>();

		// Load PostgreSQL JDBC driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // Handle the error as per your requirement
			return items;
		}

		// Establish database connection and retrieve products
		try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ITEMS)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int productId = resultSet.getInt("product_id");
				String productName = resultSet.getString("product_name");
				int count = resultSet.getInt("count");

				Item item = new Item(productId, productName, count);
				item.setCount(count);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the error as per your requirement
		}
		return items;
	}
}
