package entity;

import java.io.Serializable;

public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer cartId;
	private Integer customerId;
	private Double totalValue;

	public Cart() {
	}

	public Cart(Integer cartId, Integer customerId, Double totalValue) {
		this.cartId = cartId;
		this.customerId = customerId;
		this.totalValue = totalValue;
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

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", customerId=" + customerId + ", totalValue=" + totalValue + "]";
	}
}
