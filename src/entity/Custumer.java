package entity;

import java.io.Serializable;
import java.util.Date;

public class Custumer implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer customerId;
	private String customerName;
	private String address;
	private String contact;
	private Date birthDate;

	public Custumer() {

	}

	public Custumer(Integer customerId, String customerName, String address, String contact, Date birthDate) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.address = address;
		this.contact = contact;
		this.birthDate = birthDate;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Custumer [customerId=" + customerId + ", customerName=" + customerName + ", address=" + address
				+ ", contact=" + contact + ", birthDate=" + birthDate + "]";
	}

	
}
