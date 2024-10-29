package model;

import java.util.List;

import entity.Cart;

public interface CartDao {
	
	void insert(Cart obj);
    void update(Cart obj);
    void deleteById(Integer id);
    Cart findById(Integer id);
    List<Cart> findAll();
}
