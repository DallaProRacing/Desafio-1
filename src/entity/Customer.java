package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer customerId;
	private String customerName;
	private String address;
	private String contact;
	private Date birthDate;
	private Cart cart;
	
	public Customer() {
		
	}
	

	public Customer(Integer customerId, String customerName, String address, String contact, Date birthDate,
			Cart cart) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.address = address;
		this.contact = contact;
		this.birthDate = birthDate;
		this.cart = cart;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}	
	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", address=" + address
				+ ", contact=" + contact + ", birthDate=" + birthDate + ", cart=" + cart + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, birthDate, cart, contact, customerId, customerName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(address, other.address) && Objects.equals(birthDate, other.birthDate)
				&& Objects.equals(cart, other.cart) && Objects.equals(contact, other.contact)
				&& Objects.equals(customerId, other.customerId) && Objects.equals(customerName, other.customerName);
	}

}
