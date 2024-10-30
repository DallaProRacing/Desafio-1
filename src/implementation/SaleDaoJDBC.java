package implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Cart;
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
		sale.setDiscount(rs.getDouble("discount"));
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
		String sql = "INSERT INTO Sale (cartId, customerId, productPrice, discount, saleValue) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, obj.getCart().getCartId());
			st.setInt(2, obj.getCustomer().getCustomerId());
			st.setDouble(3, obj.getProductPrice());
			st.setDouble(4, obj.getDiscount());
			st.setDouble(5, obj.getSaleValue());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void update(Sale obj) {
		String sql = "UPDATE Sale SET cartId = ?, customerId = ?, productPrice = ?, discount = ?, saleValue = ? WHERE saleId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, obj.getCart().getCartId());
			st.setInt(2, obj.getCustomer().getCustomerId()); // Isso deve ser seguro agora
			st.setDouble(3, obj.getProductPrice());
			st.setDouble(4, obj.getDiscount());
			st.setDouble(5, obj.getSaleValue());
			st.setInt(6, obj.getSaleId());
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
		String sql = "SELECT s.saleId, s.productPrice, s.discount, s.saleValue, " + "c.cartId, c.totalValue "
				+ "FROM Sale s " + "LEFT JOIN Cart c ON s.cartId = c.cartId " + "WHERE s.saleId = ?";

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
		String sql = "SELECT s.saleId, s.productPrice, s.discount, s.saleValue, " + "c.cartId, c.totalValue "
				+ "FROM Sale s " + "LEFT JOIN Cart c ON s.cartId = c.cartId";

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

}
