package model;

import java.util.List;

import entity.Customer;

public interface CustomerDao {
	
	void insert(Customer obj);
    void update(Customer obj);
    void deleteById(Integer id);
    Customer findById(Integer id);
    List<Customer> findAll();
}
