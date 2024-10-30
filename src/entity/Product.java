package entity;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer productId;
	private String productName;
	private Integer productQuantity;
	private Double productPrice;
	private Integer categoryId;
	
	private Category category;
	
	

	public Integer getProductId() {
		return productId;
	}



	public void setProductId(Integer productId) {
		this.productId = productId;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
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



	public Integer getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}



	public Category getCategory() {
		return category;
	}



	public void setCategory(Category category) {
		this.category = category;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public int hashCode() {
		return Objects.hash(category, categoryId, productId, productName, productPrice, productQuantity);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(category, other.category) && Objects.equals(categoryId, other.categoryId)
				&& Objects.equals(productId, other.productId) && Objects.equals(productName, other.productName)
				&& Objects.equals(productPrice, other.productPrice)
				&& Objects.equals(productQuantity, other.productQuantity);
	}



	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productQuantity="
				+ productQuantity + ", productPrice=" + productPrice + ", categoryId=" + categoryId + ", category="
				+ category + "]";
	}



	public Product(Integer productId, String productName, Integer productQuantity, Double productPrice,
			Integer categoryId, Category category) {
		this.productId = productId;
		this.productName = productName;
		this.productQuantity = productQuantity;
		this.productPrice = productPrice;
		this.categoryId = categoryId;
		this.category = category;
	}



	public Product() {

	}

}
