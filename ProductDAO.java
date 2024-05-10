package shoppingcart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

	private String jdbcURL = "jdbc:postgresql://192.168.110.48:5432/plf_training";
	private String jdbcUsername = "plf_training_admin";
	private String jdbcPassword = "pff123";

	private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM product181";
	private static final String SELECT_PRODUCTS_BY_PRICE = "SELECT * FROM product181 WHERE productprice BETWEEN ? AND ?";
	private static final String SELECT_PRODUCTS_BY_CATEGORY = "SELECT * FROM product181 WHERE catid = ?";
	private static final String SELECT_PRODUCTS_BY_PRICE_AND_CATEGORY = "SELECT * FROM product181 WHERE productprice BETWEEN ? AND ? AND catid = ?";
	// SQL query to retrieve product price by ID
	private static final String SELECT_PRODUCT_PRICE_BY_ID = "SELECT productprice FROM product181 WHERE productid = ?";

	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();

		// Load PostgreSQL JDBC driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // Handle the error as per your requirement
			return products;
		}

		// Establish database connection and retrieve all products
		try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int productId = resultSet.getInt("productid");
				String productName = resultSet.getString("productname");
				int productPrice = resultSet.getInt("productprice");
				String productHsn = resultSet.getString("producthsn");
				String image = resultSet.getString("image");
				Product product = new Product(productId, productName, productPrice, productHsn, image);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the error as per your requirement
		}
		return products;
	}

	public List<Product> getAllProducts(int minPrice, int maxPrice) {
		List<Product> products = new ArrayList<>();

		// Load PostgreSQL JDBC driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // Handle the error as per your requirement
			return products;
		}

		// Establish database connection and retrieve products within price range
		try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTS_BY_PRICE)) {
			preparedStatement.setInt(1, minPrice);
			preparedStatement.setInt(2, maxPrice);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int productId = resultSet.getInt("productid");
				String productName = resultSet.getString("productname");
				int productPrice = resultSet.getInt("productprice");
				String productHsn = resultSet.getString("producthsn");
				String image = resultSet.getString("image");
				Product product = new Product(productId, productName, productPrice, productHsn, image);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the error as per your requirement
		}
		return products;
	}

	public List<Product> getAllProducts(int categoryId) {
		List<Product> products = new ArrayList<>();

		// Load PostgreSQL JDBC driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // Handle the error as per your requirement
			return products;
		}

		// Establish database connection and retrieve products for the specified category
		try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTS_BY_CATEGORY)) {
			preparedStatement.setInt(1, categoryId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int productId = resultSet.getInt("productid");
				String productName = resultSet.getString("productname");
				int productPrice = resultSet.getInt("productprice");
				String productHsn = resultSet.getString("producthsn");
				String image = resultSet.getString("image");
				Product product = new Product(productId, productName, productPrice, productHsn, image);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the error as per your requirement
		}
		return products;
	}

	public List<Product> getAllProducts(int minPrice, int maxPrice, int categoryId) {
		List<Product> products = new ArrayList<>();

		// Load PostgreSQL JDBC driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // Handle the error as per your requirement
			return products;
		}

		// Establish database connection and retrieve products within price range and category
		try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_PRODUCTS_BY_PRICE_AND_CATEGORY)) {
			preparedStatement.setInt(1, minPrice);
			preparedStatement.setInt(2, maxPrice);
			preparedStatement.setInt(3, categoryId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int productId = resultSet.getInt("productid");
				String productName = resultSet.getString("productname");
				int productPrice = resultSet.getInt("productprice");
				String productHsn = resultSet.getString("producthsn");
				String image = resultSet.getString("image");
				Product product = new Product(productId, productName, productPrice, productHsn, image);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the error as per your requirement
		}
		return products;
	}

	public int getProductPrice(int productId) {
		int price = -1;
		try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_PRICE_BY_ID)) {
			preparedStatement.setInt(1, productId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				price = resultSet.getInt("productprice");
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the error as per your requirement
		}
		return price;
	}

	public static List<Item> getAllCartItemsWithPrice() {
		List<Item> items = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = YourDatabaseConnection.getConnection();
			String query = "SELECT c.product_id, c.product_name, p.productprice, SUM(c.product_id) AS total_quantity "
					+ "FROM carts181 c " + "JOIN products181 p ON c.product_id = p.productid "
					+ "GROUP BY c.product_id, c.product_name, p.productprice";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				// Retrieve data from the result set and create Item objects
				int productId = resultSet.getInt("product_id");
				String productName = resultSet.getString("product_name");
				int productPrice = resultSet.getInt("productprice");
				int quantity = resultSet.getInt("total_quantity");
				// Assuming you have a constructor for the Item class that takes these parameters
				Item item = new Item(productId, productName, productPrice, quantity);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle exceptions appropriately
		} finally {
			// Close resources
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return items;
	}

}
