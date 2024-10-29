package model;

import java.util.List;

import entity.Product;

public interface ProductDao {
	
	void insert(Product obj);
    void update(Product obj);
    void deleteById(Integer id);
    Product findById(Integer id);
    List<Product> findAll();
}
