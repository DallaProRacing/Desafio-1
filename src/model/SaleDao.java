package model;

import java.util.List;

import entity.Sale;

public interface SaleDao {
	
	void insert(Sale obj);
    void update(Sale obj);
    void deleteById(Integer id);
    Sale findById(Integer id);
    List<Sale> findAll();
}
