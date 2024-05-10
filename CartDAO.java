package shoppingcart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDAO {
	// JDBC connection details
	private static final String URL = "jdbc:postgresql://192.168.110.48:5432/plf_training";
	private static final String USERNAME = "plf_training_admin";
	private static final String PASSWORD = "pff123";

	// SQL query to check if the product ID and pincode combination is valid
	private static final String VALIDATE_PINCODE_SQL = "SELECT COUNT(*) FROM pincodes_181 WHERE productid = ? AND pincode = ?";

	public boolean validatePincode(int productId, int pincode) {
		try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(VALIDATE_PINCODE_SQL)) {
			stmt.setInt(1, productId);
			stmt.setInt(2, pincode);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0; // If count > 0, pincode is valid for the product ID
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void insertCartItem(int productId, String productName, int pincode) {
		// Validate pincode before inserting into the cart
		if (!validatePincode(productId, pincode)) {
			System.out.println("product not deliverable to that region.");
			return;
		}

		// Load PostgreSQL JDBC driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO carts181 (product_id, product_name) VALUES (?, ?)")) {
			stmt.setInt(1, productId);
			stmt.setString(2, productName);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
