package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import Conection.DB;
import entity.Cart;
import entity.CartItems;
import entity.Category;
import entity.Custumer;
import entity.Inventory;
import entity.Product;
import entity.Sale;

public class Program {

	public static void main(String[] args) {

		
		  Connection conn = null; Statement st = null; ResultSet rs = null;
		  
		  try { conn = DB.getConnection(); System.out.println("Conexão bem-sucedida!");
		  
		  st = conn.createStatement(); rs = st.executeQuery("SELECT * FROM Customer");
		  
		 while (rs.next()) { System.out.println("ID: " + rs.getInt("customerId") +
		 ", Name: " + rs.getString("customerName")); } } catch (Exception e) {
		 e.printStackTrace(); } finally {
		 
		  DB.closeResultSet(rs); DB.closeStatement(st); DB.closeConnection(); }
		 

		// Testando a classe Customer

		Custumer customer1 = new Custumer(1, "John Doe", "123 Main St", "555-1234",
				java.sql.Date.valueOf("1990-01-01"));
		System.out.println("Customer: " + customer1);

		// Testando a classe Category
		Category category1 = new Category(1, "Electronics");
		System.out.println("Category: " + category1);

		// Testando a classe Product
		Product product1 = new Product(1, "Smartphone", 10, 1200.00, category1.getCategoryId());
		System.out.println("Product: " + product1);

		// Testando a classe Inventory
		Inventory inventory1 = new Inventory(1, product1.getProductId(), 50);
		System.out.println("Inventory: " + inventory1);

		// Testando a classe Cart
		Cart cart1 = new Cart(1, customer1.getCustomerId(), 0.00);
		System.out.println("Cart: " + cart1);

		// Testando a classe CartItems
		CartItems cartItem1 = new CartItems(1, cart1.getCartId(), product1.getProductId(), 2,
				product1.getProductPrice());
		System.out.println("CartItem: " + cartItem1);

		// Testando a classe Sale
		Sale sale1 = new Sale(1, cart1.getCartId(), customer1.getCustomerId(), product1.getProductPrice(), 50.00,
				1150.00);
		System.out.println("Sale: " + sale1);

		// Atualizando alguns valores para testar os setters
		customer1.setCustomerName("Jane Doe");
		category1.setCategoryName("Home Appliances");
		product1.setProductName("Washing Machine");
		inventory1.setQuantity(30);
		cart1.setTotalValue(2400.00);
		cartItem1.setProductQuantity(1);
		sale1.setDiscount(100.00);

		// Exibindo os valores atualizados
		System.out.println("\nUpdated Customer: " + customer1);
		System.out.println("Updated Category: " + category1);
		System.out.println("Updated Product: " + product1);
		System.out.println("Updated Inventory: " + inventory1);
		System.out.println("Updated Cart: " + cart1);
		System.out.println("Updated CartItem: " + cartItem1);
		System.out.println("Updated Sale: " + sale1);

	}
}
