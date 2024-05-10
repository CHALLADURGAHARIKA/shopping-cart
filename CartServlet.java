package shoppingcart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve productId and productName from the request
		String productId = request.getParameter("productId");
		String productName = request.getParameter("productName");

		// Retrieve the session
		HttpSession session = request.getSession();

		// Retrieve the cart items from session
		List<Product> cartItems = (List<Product>) session.getAttribute("cartItems");

		// If cartItems is null, create a new list
		if (cartItems == null) {
			cartItems = new ArrayList<>();
		}

		// Check if the product is already in the cart
		boolean found = false;
		for (Product item : cartItems) {
			if (item.getProductId() == Integer.parseInt(productId)) {
				// If found, increase the quantity
				item.setQuantity(item.getQuantity() + 1);
				found = true;
				break;
			}
		}

		// If not found, add it as a new item
		if (!found) {
			Product newItem = new Product(Integer.parseInt(productId), productName, 1); // Quantity starts from 1
			cartItems.add(newItem);
		}

		// Update the cart items in session
		session.setAttribute("cartItems", cartItems);

		// Serialize the cartItems list to JSON manually
		StringBuilder cartItemsJson = new StringBuilder("[");
		for (Product item : cartItems) {
			if (cartItemsJson.length() > 1) {
				cartItemsJson.append(",");
			}
			cartItemsJson.append("{\"productId\":").append(item.getProductId()).append(",\"productName\":\"")
					.append(item.getProductName()).append("\",\"quantity\":").append(item.getQuantity()).append("}");
		}
		cartItemsJson.append("]");
		session.setAttribute("cartItemsJson", cartItemsJson.toString());

		// Redirect back to the product list page
		response.sendRedirect("products.jsp");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Display cart items logic can go here if needed
	}
}
