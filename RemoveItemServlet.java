package shoppingcart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RemoveItemServlet")
public class RemoveItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve productId parameter from request
		int productId = Integer.parseInt(request.getParameter("productId"));

		// Database connection parameters
		String jdbcURL = "jdbc:postgresql://192.168.110.48:5432/plf_training";
		String jdbcUsername = "plf_training_admin";
		String jdbcPassword = "pff123";

		try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
			// Prepare SQL statement to remove item from carts181 table
			String sql = "DELETE FROM carts181 WHERE product_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, productId);

			// Execute SQL statement
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				// Item successfully removed from the cart
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				// No item found with the provided productId
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (SQLException e) {
			// Handle any database errors
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
