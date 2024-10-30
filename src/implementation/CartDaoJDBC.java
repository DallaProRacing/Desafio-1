package implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Cart;
import entity.Customer;
import model.CartDao;

public class CartDaoJDBC implements CartDao {

	private Connection conn;

	public CartDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	private Cart instantiateCart(ResultSet rs, Customer customer) throws SQLException {
		Cart cart = new Cart();
		cart.setCartId(rs.getInt("cartId"));
		Double totalValue = rs.getObject("totalValue", Double.class);
		cart.setTotalValue(totalValue != null ? totalValue : 0.0);		    
		cart.setCustomer(customer);
		return cart;
	}

	private Customer instantiateCustomer(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setCustomerId(rs.getInt("customerId"));
		return customer;
	}

	@Override
	public void insert(Cart obj) {
		String sql = "INSERT INTO Cart (customerId, totalValue) VALUES (?, ?)";

		try (PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			st.setInt(1, obj.getCustomer().getCustomerId());
			st.setDouble(2, obj.getTotalValue());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				try (ResultSet rs = st.getGeneratedKeys()) {
					if (rs.next()) {
						obj.setCartId(rs.getInt(1)); // Define o cartId gerado
					}
				}
			} else {
				throw new SQLException("Erro inesperado! Nenhuma linha foi afetada.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void update(Cart obj) {
		String sql = "UPDATE Cart SET customerId = ?, totalValue = ? WHERE cartId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, obj.getCustomer().getCustomerId());
			st.setDouble(2, obj.getTotalValue());
			st.setInt(3, obj.getCartId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Integer id) {
		String sql = "DELETE FROM Cart WHERE cartId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public Cart findById(Integer id) {
		String sql = "SELECT c.cartId, c.totalValue, cu.customerId " + "FROM Cart c "
				+ "LEFT JOIN Customer cu ON c.customerId = cu.customerId " + "WHERE c.cartId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				Customer customer = instantiateCustomer(rs);
				return instantiateCart(rs, customer);
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<Cart> findAll() {
		String sql = "SELECT c.cartId, c.totalValue, cu.customerId " + "FROM Cart c "
				+ "LEFT JOIN Customer cu ON c.customerId = cu.customerId";

		try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
			List<Cart> list = new ArrayList<>();
			while (rs.next()) {
				Customer customer = instantiateCustomer(rs);
				Cart cart = instantiateCart(rs, customer);
				list.add(cart);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
