package entity;

import java.io.Serializable;
import java.util.Objects;

public class CartItems implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer cartItemId;
	private Integer cartId;
	private Integer productId;
	private Integer productQuantity;
	private Double productPrice;

	private Cart cart;
	private Product product;

	public CartItems() {
	}

	public CartItems(Integer cartItemId, Integer cartId, Integer productId, Integer productQuantity,
			Double productPrice, Cart cart, Product product) {
		this.cartItemId = cartItemId;
		this.cartId = cartId;
		this.productId = productId;
		this.productQuantity = productQuantity;
		this.productPrice = productPrice;
		this.cart = cart;
		this.product = product;
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

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "CartItems [cartItemId=" + cartItemId + ", cartId=" + cartId + ", productId=" + productId
				+ ", productQuantity=" + productQuantity + ", productPrice=" + productPrice + ", cart=" + cart
				+ ", product=" + product + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cart, cartId, cartItemId, product, productId, productPrice, productQuantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItems other = (CartItems) obj;
		return Objects.equals(cart, other.cart) && Objects.equals(cartId, other.cartId)
				&& Objects.equals(cartItemId, other.cartItemId) && Objects.equals(product, other.product)
				&& Objects.equals(productId, other.productId) && Objects.equals(productPrice, other.productPrice)
				&& Objects.equals(productQuantity, other.productQuantity);
	}

}
