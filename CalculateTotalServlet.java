package shoppingcart;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CalculateTotalServlet")
public class CalculateTotalServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int total = 0;
		ProductDAO productDAO = new ProductDAO();
		List<Item> items = (List<Item>) request.getAttribute("items");
		if (items != null) {
			for (Item item : items) {
				int productId = item.getProductId();
				int quantity = item.getCount();
				int price = productDAO.getProductPrice(productId);
				total += (quantity * price);
				System.out.println("total");
			}
		}

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(String.valueOf(total));
		System.out.println("total" + total);
	}
}
