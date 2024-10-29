package model;

import java.util.List;

import entity.CartItems;

public interface CartItemsDao {
	
	void insert(CartItems obj);
    void update(CartItems obj);
    void deleteById(Integer id);
    CartItems findById(Integer id);
    List<CartItems> findAll();
}
