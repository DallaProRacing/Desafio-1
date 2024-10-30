package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import Conection.DB;
import entity.Cart;
import entity.CartItems;
import entity.Product;
import implementation.CartItemsDaoJDBC;

public class Program {
	
    public static void main(String[] args) {
        
    	Connection conn = null;

        conn = DB.getConnection();
		CartItemsDaoJDBC cartItemsDao = new CartItemsDaoJDBC(conn);
		
		// Criar um novo produto e um novo carrinho para o teste
		Product product = new Product();
		product.setProductId(1); // Defina o ID do produto que já existe no banco
		product.setProductName("Test Product");

		Cart cart = new Cart();
		cart.setCartId(1); // Defina o ID do carrinho que já existe no banco
		cart.setTotalValue(0.0); // Total inicial

		// Inserir um novo CartItem
		CartItems newCartItem = new CartItems();
		newCartItem.setCart(cart);
		newCartItem.setProduct(product);
		newCartItem.setProductQuantity(2);
		newCartItem.setProductPrice(19.99);
		cartItemsDao.insert(newCartItem);
		System.out.println("Inserted new CartItem.");

		// Encontrar todos os CartItems
		List<CartItems> allCartItems = cartItemsDao.findAll();
		System.out.println("All CartItems:");
		for (CartItems item : allCartItems) {
		    System.out.println(item.getCartItemId() + ": " + item.getProduct().getProductName() +
		                       " - Quantity: " + item.getProductQuantity());
		}

		// Atualizar um CartItem
		if (!allCartItems.isEmpty()) {
		    CartItems itemToUpdate = allCartItems.get(0);
		    itemToUpdate.setProductQuantity(3); // Altera a quantidade
		    cartItemsDao.update(itemToUpdate);
		    System.out.println("Updated CartItem with ID: " + itemToUpdate.getCartItemId());
		}

		// Encontrar um CartItem por ID
		if (!allCartItems.isEmpty()) {
		    CartItems foundItem = cartItemsDao.findById(allCartItems.get(0).getCartItemId());
		    System.out.println("Found CartItem: " + foundItem.getCartItemId() + " - " +
		                       foundItem.getProduct().getProductName());
		}

		// Deletar um CartItem
		if (!allCartItems.isEmpty()) {
		    CartItems itemToDelete = allCartItems.get(0);
		    cartItemsDao.deleteById(itemToDelete.getCartItemId());
		    System.out.println("Deleted CartItem with ID: " + itemToDelete.getCartItemId());
		}
    }
}
