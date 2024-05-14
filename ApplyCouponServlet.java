package shoppingcart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ApplyCouponServlet")
public class ApplyCouponServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String couponCode = request.getParameter("couponCode");
		double finalBill = Double.parseDouble(request.getParameter("finalBill"));
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = YourDatabaseConnection.getConnection(); // Implement your database connection method

			// Check coupon validity and apply if valid
			String query = "SELECT * FROM coupons181 WHERE dcpn_code = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, couponCode);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int couponId = resultSet.getInt("dcpn_id");
				int couponValue = resultSet.getInt("dcpn_value");
				int couponCount = resultSet.getInt("dcpncount");
				int couponMinValue = resultSet.getInt("dcpn_min_value");

				if (finalBill >= couponMinValue && couponCount > 0) {
					// Apply coupon
					finalBill -= couponValue;
					// Update coupon count in database
					String updateQuery = "UPDATE coupons181 SET dcpncount = ? WHERE dcpn_id = ?";
					statement = connection.prepareStatement(updateQuery);
					statement.setInt(1, couponCount - 1);
					statement.setInt(2, couponId);
					statement.executeUpdate();
					// Insert order details into order_181 table
					String insertQuery = "INSERT INTO order_181 (dcpn_id, order_id, order_total) VALUES (?, ?, ?)";
					statement = connection.prepareStatement(insertQuery);
					statement.setInt(1, couponId);
					statement.setString(2, generateRandomOrderId()); // Implement this method to generate random order
																		// ID
					statement.setDouble(3, finalBill);
					statement.executeUpdate();
				} else {
					// Coupon not applicable
					response.getWriter().write("invalid");
					return;
				}
			} else {
				// Coupon not found
				response.getWriter().write("not_found");
				return;
			}

			// Update product stock table
			String productQuery = "SELECT c.product_id, COUNT(*) AS quantity FROM carts181 c GROUP BY c.product_id";
			statement = connection.prepareStatement(productQuery);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int productId = resultSet.getInt("product_id");
				int quantity = resultSet.getInt("quantity");

				// Update product stock table
				String updateStockQuery = "UPDATE productstock181 SET stock = stock - ? WHERE productid = ?";
				statement = connection.prepareStatement(updateStockQuery);
				statement.setInt(1, quantity);
				statement.setInt(2, productId);
				statement.executeUpdate();
			}

			// Return success
			response.getWriter().write("success");
		} catch (SQLException e) {
			e.printStackTrace(); // Handle database errors appropriately
			response.getWriter().write("database_error");
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
	}

	// Method to generate random order ID
	private String generateRandomOrderId() {
		// Implement random order ID generation logic here
		return "ORD" + (int) (Math.random() * 10000);
	}
}
