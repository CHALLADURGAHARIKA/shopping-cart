package shoppingcart;

import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	private int productId;
	private String productName;
	private int productPrice;
	private String productHsn;
	private int quantity;
	private String image;

	public Product(int productId, String productName) {

		this.productId = productId;
		this.productName = productName;
		// Default constructor
	}

	public Product(int productId, String productName, int productPrice, String productHsn, String image) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productHsn = productHsn;
		this.image = image;
	}

	public Product(int productId, String productName, int quantity) {
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
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

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProductHsn() {
		return productHsn;
	}

	public void setProductHsn(String productHsn) {
		this.productHsn = productHsn;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
