package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import Conection.DB;
import entity.Cart;
import entity.Customer;
import entity.Sale;
import implementation.SaleDaoJDBC;

public class Program {

	public static void main(String[] args) {

		Connection conn = null;

		conn = DB.getConnection();
		try {
			conn = DB.getConnection();
			SaleDaoJDBC saleDao = new SaleDaoJDBC(conn);

			Cart cart = new Cart();
			cart.setCartId(1); // Defina o ID do carrinho que já existe no banco
			cart.setTotalValue(0.0); // Total inicial

			Customer customer = new Customer();
			customer.setCustomerId(1); // Defina o ID do cliente que já existe no banco

			// Inserir uma nova venda
			Sale newSale = new Sale();
			newSale.setCart(cart);
			newSale.setCustomer(customer); // Definindo o cliente corretamente
			newSale.setProductPrice(49.99);
			newSale.setDiscount(5.00);
			newSale.setSaleValue(44.99); // Valor após desconto
			saleDao.insert(newSale); // Isso deve funcionar agora
			System.out.println("Inserted new Sale.");

			// Encontrar todas as vendas
			List<Sale> allSales = saleDao.findAll();
			System.out.println("All Sales:");
			for (Sale sale : allSales) {
				System.out.println(sale.getSaleId() + ": Price: " + sale.getProductPrice() + ", Discount: "
						+ sale.getDiscount() + ", Total: " + sale.getSaleValue());
			}

			// Atualizar uma venda
			if (!allSales.isEmpty()) {
				Sale saleToUpdate = allSales.get(0);
				saleToUpdate.setProductPrice(39.99); // Altera o preço
				saleDao.update(saleToUpdate);
				System.out.println("Updated Sale with ID: " + saleToUpdate.getSaleId());
			}

			// Encontrar uma venda por ID
			if (!allSales.isEmpty()) {
				Sale foundSale = saleDao.findById(allSales.get(0).getSaleId());
				System.out.println("Found Sale: " + foundSale.getSaleId() + " - Price: " + foundSale.getProductPrice()
						+ ", Total: " + foundSale.getSaleValue());
			}

			// Deletar uma venda
			if (!allSales.isEmpty()) {
				Sale saleToDelete = allSales.get(0);
				saleDao.deleteById(saleToDelete.getSaleId());
				System.out.println("Deleted Sale with ID: " + saleToDelete.getSaleId());
			}
		}finally {
			DB.closeConnection();
		}
	}
}
