package shoppingcart;

public class CartController {
	private CartDAO cartDAO;

	public CartController() {
		this.cartDAO = new CartDAO();
	}

	public void addToCart(int productId, String productName, int pincode) {
		cartDAO.insertCartItem(productId, productName, pincode);
	}
}
