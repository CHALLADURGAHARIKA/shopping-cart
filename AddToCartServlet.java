package shoppingcart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {
	private CartController cartController;

	@Override
	public void init() throws ServletException {
		super.init();
		cartController = new CartController();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("productId"));
		String productName = request.getParameter("productName");
		int pincode = Integer.parseInt(request.getParameter("pincode"));

		// Add to cart
		cartController.addToCart(productId, productName, pincode);
		// Prepare JavaScript response

		// Redirect to some page after adding to cart

		// Set response content type
		response.setContentType("text/html");

		// Write response message
		// response.getWriter().println("<html><body>");
		// response.getWriter().println("<script>alert('Product added successfully!');</script>");
		// response.getWriter().println("</body></html>");

	}
}
