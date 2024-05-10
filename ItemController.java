package shoppingcart;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemController {
	private ItemsDAO itemDAO;

	public ItemController() {
		this.itemDAO = new ItemsDAO();
	}

	public void getAllItems(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Item> items = itemDAO.getAllItems();
		request.setAttribute("items", items);
		request.getRequestDispatcher("items.jsp").forward(request, response);
	}
}
