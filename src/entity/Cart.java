package entity;

import java.io.Serializable;
import java.util.Objects;

public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer cartId;
	private Integer customerId;
	private Double totalValue;

	private Customer customer;

	public Cart() {
	}

	public Cart(Integer cartId, Integer customerId, Double totalValue, Customer customer) {
		super();
		this.cartId = cartId;
		this.customerId = customerId;
		this.totalValue = totalValue;
		this.customer = customer;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", customerId=" + customerId + ", totalValue=" + totalValue + ", customer="
				+ customer + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId, customer, customerId, totalValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		return Objects.equals(cartId, other.cartId) && Objects.equals(customer, other.customer)
				&& Objects.equals(customerId, other.customerId) && Objects.equals(totalValue, other.totalValue);
	}

}
