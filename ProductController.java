package shoppingcart;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * public class ProductController { private ProductDAO productDAO;
 * 
 * public ProductController() { this.productDAO = new ProductDAO(); }
 * 
 * public void getAllProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException,
 * IOException { int minPrice = 0; int maxPrice = Integer.MAX_VALUE; // Default maximum price
 * 
 * // Get the minimum and maximum prices from the request parameters if provided String minPriceParam =
 * request.getParameter("minPrice"); String maxPriceParam = request.getParameter("maxPrice");
 * 
 * if (minPriceParam != null && !minPriceParam.isEmpty()) { minPrice = Integer.parseInt(minPriceParam); } if
 * (maxPriceParam != null && !maxPriceParam.isEmpty()) { maxPrice = Integer.parseInt(maxPriceParam); }
 * 
 * List<Product> products;
 * 
 * // Check if min and max prices are provided if (minPrice != 0 || maxPrice != Integer.MAX_VALUE) { // Call the
 * parameterized getAllProducts method products = productDAO.getAllProducts(minPrice, maxPrice); } else { // Call the
 * normal getAllProducts method products = productDAO.getAllProducts(); }
 * 
 * request.setAttribute("products", products); request.getRequestDispatcher("products.jsp").forward(request, response);
 * } }
 * 
 * 
 */

public class ProductController {

	private ProductDAO productDAO;

	public ProductController() {
		this.productDAO = new ProductDAO();
	}

	public void getAllProducts(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE; // Default maximum price
		int categoryId = 0; // Default category ID

		// Get the minimum and maximum prices from the request parameters if provided
		String minPriceParam = request.getParameter("minPrice");
		String maxPriceParam = request.getParameter("maxPrice");
		String categoryIdParam = request.getParameter("category");

		if (minPriceParam != null && !minPriceParam.isEmpty()) {
			minPrice = Integer.parseInt(minPriceParam);
		}
		if (maxPriceParam != null && !maxPriceParam.isEmpty()) {
			maxPrice = Integer.parseInt(maxPriceParam);
		}
		if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
			categoryId = Integer.parseInt(categoryIdParam);
		}

		List<Product> products;

		// Check if min and max prices are provided
		if (minPrice != 0 || maxPrice != Integer.MAX_VALUE) {
			// Call the parameterized getAllProducts method
			products = productDAO.getAllProducts(minPrice, maxPrice, categoryId);
		} else if (categoryId != 0) {
			// Call the getAllProducts method with category ID
			products = productDAO.getAllProducts(categoryId);
		} else {
			// Call the normal getAllProducts method
			products = productDAO.getAllProducts();
		}

		request.setAttribute("products", products);
		request.getRequestDispatcher("products.jsp").forward(request, response);
	}
}
