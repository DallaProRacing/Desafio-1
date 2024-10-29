package entity;

import java.io.Serializable;

public class CartItems implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer cartItemId;
	private Integer cartId;
	private Integer productId;
	private Integer productQuantity;
	private Double productPrice;

	public CartItems() {
	}

	public CartItems(Integer cartItemId, Integer cartId, Integer productId, Integer productQuantity,
			Double productPrice) {
		this.cartItemId = cartItemId;
		this.cartId = cartId;
		this.productId = productId;
		this.productQuantity = productQuantity;
		this.productPrice = productPrice;
	}

	public Integer getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Integer cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	@Override
	public String toString() {
		return "CartItems [cartItemId=" + cartItemId + ", cartId=" + cartId + ", productId=" + productId
				+ ", productQuantity=" + productQuantity + ", productPrice=" + productPrice + "]";
	}
}
