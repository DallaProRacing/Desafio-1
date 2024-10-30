package entity;

import java.io.Serializable;
import java.util.Objects;

public class Inventory implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer inventoryId;
	private Integer productId;
	private Integer quantity;
	
	private Product product;

	public Inventory() {
	}

	public Inventory(Integer inventoryId, Integer productId, Integer quantity, Product product) {
		this.inventoryId = inventoryId;
		this.productId = productId;
		this.quantity = quantity;
		this.product = product;
	}

	public Integer getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Inventory [inventoryId=" + inventoryId + ", productId=" + productId + ", quantity=" + quantity
				+ ", product=" + product + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(inventoryId, product, productId, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		return Objects.equals(inventoryId, other.inventoryId) && Objects.equals(product, other.product)
				&& Objects.equals(productId, other.productId) && Objects.equals(quantity, other.quantity);
	}
	
	
}
