package shoppingcart;

public class Item {
	private int productId;
	private String productName;
	private int count;
	private int productPrice;
	private int quantity;

	// Constructor with four parameters
	public Item(int productId, String productName, int productPrice, int quantity) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.quantity = quantity;
	}

	public Item(int productId, String productName, int count) {

		this.productId = productId;
		this.productName = productName;
		this.count = 1;
		// Default constructor
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
