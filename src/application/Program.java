package application;

import java.sql.Connection;
import java.sql.SQLException;

import Conection.DB;
import entity.Cart;
import entity.Customer;
import implementation.CartDaoJDBC;
import model.CartDao;

public class Program {

	public static void main(String[] args) {

		Connection conn = null;

		conn = DB.getConnection();
		conn = DB.getConnection();

		CartDao cartDao = new CartDaoJDBC(conn);

		// Teste de inserção
		Customer customer = new Customer();
		customer.setCustomerId(1); // Altere para um Customer existente

		Cart newCart = new Cart();
		newCart.setCustomer(customer);
		newCart.setTotalValue(100.50); // Exemplo de valor total

		cartDao.insert(newCart);
		System.out.println("Cart inserido com ID: " + newCart.getCartId());

		// Teste de busca
		Cart foundCart = cartDao.findById(newCart.getCartId());
		System.out.println("Cart encontrado: ID = " + foundCart.getCartId() + ", Total Value = " + foundCart.getTotalValue());

		// Teste de atualização
		foundCart.setTotalValue(150.75); // Alterar o valor total
		cartDao.update(foundCart);
		System.out.println("Cart atualizado: ID = " + foundCart.getCartId() + ", Novo Total Value = " + foundCart.getTotalValue());

		// Teste de exclusão
		cartDao.deleteById(foundCart.getCartId());
		System.out.println("Cart excluído com ID: " + foundCart.getCartId());
	}
}

