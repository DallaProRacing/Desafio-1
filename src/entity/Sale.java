package entity;

import java.io.Serializable;
import java.util.Objects;

public class Sale implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer saleId;
	private Integer cartId;
	private Integer customerId;
	private Double productPrice;	
	private Double saleValue;
	private Customer customer;
	private Cart cart;

	public Sale() {
	}

	public Sale(Integer saleId, Integer cartId, Integer customerId, Double productPrice, Double saleValue,
			Customer customer, Cart cart) {
		
		this.saleId = saleId;
		this.cartId = cartId;
		this.customerId = customerId;
		this.productPrice = productPrice;
		this.saleValue = saleValue;
		this.customer = customer;
		this.cart = cart;
	}

	public Integer getSaleId() {
		return saleId;
	}

	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Double getSaleValue() {
		return saleValue;
	}

	public void setSaleValue(Double saleValue) {
		this.saleValue = saleValue;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public String toString() {
		return "Sale [saleId=" + saleId + ", cartId=" + cartId + ", customerId=" + customerId + ", productPrice="
				+ productPrice + ", saleValue=" + saleValue + ", customer=" + customer + ", cart=" + cart + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cart, cartId, customer, customerId, productPrice, saleId, saleValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		return Objects.equals(cart, other.cart) && Objects.equals(cartId, other.cartId)
				&& Objects.equals(customer, other.customer) && Objects.equals(customerId, other.customerId)
				&& Objects.equals(productPrice, other.productPrice) && Objects.equals(saleId, other.saleId)
				&& Objects.equals(saleValue, other.saleValue);
	}

	

}
