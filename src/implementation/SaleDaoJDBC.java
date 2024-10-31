package implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Cart;
import entity.CartItems;
import entity.Customer;
import entity.Sale;
import model.SaleDao;

public class SaleDaoJDBC implements SaleDao {
	private Connection conn;

	public SaleDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	private Sale instantiateSale(ResultSet rs, Cart cart) throws SQLException {
		Sale sale = new Sale();
		sale.setSaleId(rs.getInt("saleId"));
		sale.setCart(cart);
		sale.setProductPrice(rs.getDouble("productPrice"));
		sale.setSaleValue(rs.getDouble("saleValue"));
		return sale;
	}

	private Cart instantiateCart(ResultSet rs) throws SQLException {
		Cart cart = new Cart();
		cart.setCartId(rs.getInt("cartId"));
		cart.setTotalValue(rs.getDouble("totalValue"));
		return cart;
	}

	private Customer instantiateCustomer(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setCustomerId(rs.getInt("customerId"));
		return customer;
	}

	@Override
	public void insert(Sale obj) {
		String sql = "INSERT INTO Sale (cartId, customerId, productPrice, saleValue) VALUES (?, ?, ?, ?)";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, obj.getCart().getCartId());
			st.setInt(2, obj.getCustomer().getCustomerId());
			st.setDouble(3, obj.getProductPrice());
			st.setDouble(4, obj.getSaleValue());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void update(Sale obj) {
		String sql = "UPDATE Sale SET cartId = ?, customerId = ?, productPrice = ?, saleValue = ? WHERE saleId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, obj.getCart().getCartId());
			st.setInt(2, obj.getCustomer().getCustomerId());
			st.setDouble(3, obj.getProductPrice());
			st.setDouble(4, obj.getSaleValue());
			st.setInt(5, obj.getSaleId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Integer id) {
		String sql = "DELETE FROM Sale WHERE saleId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Sale findById(Integer id) {
		String sql = "SELECT s.saleId, s.productPrice, s.saleValue, " + "c.cartId, c.totalValue " + "FROM Sale s "
				+ "LEFT JOIN Cart c ON s.cartId = c.cartId " + "WHERE s.saleId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				Cart cart = instantiateCart(rs);
				Sale sale = instantiateSale(rs, cart);
				return sale;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<Sale> findAll() {
		String sql = "SELECT s.saleId, s.productPrice, s.saleValue, " + "c.cartId, c.totalValue " + "FROM Sale s "
				+ "LEFT JOIN Cart c ON s.cartId = c.cartId";

		try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

			List<Sale> list = new ArrayList<>();
			while (rs.next()) {
				Cart cart = instantiateCart(rs);
				Sale sale = instantiateSale(rs, cart);
				list.add(sale);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void finalizeSale(Cart cart, Customer customer) {
		String sql = "INSERT INTO Sale (cartId, customerId, productPrice, saleValue) VALUES (?, ?, ?, ?)";

		try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, cart.getCartId());
			statement.setInt(2, customer.getCustomerId());

			// Para o preço total, você pode somar todos os preços dos produtos do carrinho
			double totalSaleValue = cart.getTotalValue();
			statement.setDouble(3, totalSaleValue); // Preço total dos produtos (você pode ajustar essa lógica se
													// necessário)
			statement.setDouble(4, totalSaleValue); // Valor total da venda

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int saleId = generatedKeys.getInt(1);
						System.out.println("Sale completed successfully with ID: " + saleId);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
