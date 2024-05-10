package shoppingcart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Item> items = ProductDAO.getAllCartItemsWithPrice();

		ProductDAO p = new ProductDAO();
		// Insert items into orders table
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = YourDatabaseConnection.getConnection(); // Implement your database connection method
			String query = "INSERT INTO orders181 (productid, productname, quantity, productprice) VALUES (?, ?, ?, ?)";
			statement = connection.prepareStatement(query);

			for (Item item : items) {
				statement.setInt(1, item.getProductId());
				statement.setString(2, item.getProductName());
				statement.setInt(3, item.getCount());
				int productPrice = p.getProductPrice(item.getProductId()); // Implement this method
				statement.setInt(4, productPrice);
				statement.executeUpdate();
			}

			// Redirect to checkout.jsp
			response.sendRedirect("checkout.jsp");
		} catch (SQLException e) {
			e.printStackTrace(); // Handle database errors appropriately
		} finally {
			// Close resources
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
