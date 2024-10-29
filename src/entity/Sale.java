package entity;

import java.io.Serializable;

public class Sale implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer saleId;
	private Integer cartId;
	private Integer customerId;
	private Double productPrice;
	private Double discount;
	private Double saleValue;

	public Sale() {
	}

	public Sale(Integer saleId, Integer cartId, Integer customerId, Double productPrice, Double discount,
			Double saleValue) {
		this.saleId = saleId;
		this.cartId = cartId;
		this.customerId = customerId;
		this.productPrice = productPrice;
		this.discount = discount;
		this.saleValue = saleValue;
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

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getSaleValue() {
		return saleValue;
	}

	public void setSaleValue(Double saleValue) {
		this.saleValue = saleValue;
	}

	@Override
	public String toString() {
		return "Sale [saleId=" + saleId + ", cartId=" + cartId + ", customerId=" + customerId + ", productPrice="
				+ productPrice + ", discount=" + discount + ", saleValue=" + saleValue + "]";
	}
}
