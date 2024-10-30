package implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Cart;
import entity.CartItems;
import entity.Product;
import model.CartItemsDao;

public class CartItemsDaoJDBC implements CartItemsDao {

	private Connection conn;

	public CartItemsDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	private CartItems instantiateCartItem(ResultSet rs, Cart cart, Product product) throws SQLException {
		CartItems item = new CartItems();
		item.setCartItemId(rs.getInt("cartItemId"));
		item.setCart(cart);
		item.setProduct(product);
		item.setProductQuantity(rs.getInt("productQuantity"));
		item.setProductPrice(rs.getDouble("productPrice"));
		return item;
	}

	private Product instantiateProduct(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setProductId(rs.getInt("productId"));
		product.setProductName(rs.getString("productName"));
		return product;
	}

	private Cart instantiateCart(ResultSet rs) throws SQLException {
		Cart cart = new Cart();
		cart.setCartId(rs.getInt("cartId"));
		cart.setTotalValue(rs.getDouble("totalValue"));
		return cart;
	}

	@Override
	public void insert(CartItems obj) {
		String sql = "INSERT INTO CartItems (cartId, productId, productQuantity, productPrice) VALUES (?, ?, ?, ?)";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, obj.getCart().getCartId());
			st.setInt(2, obj.getProduct().getProductId());
			st.setInt(3, obj.getProductQuantity());
			st.setDouble(4, obj.getProductPrice());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void update(CartItems obj) {
		String sql = "UPDATE CartItems SET cartId = ?, productId = ?, productQuantity = ?, productPrice = ? WHERE cartItemId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, obj.getCart().getCartId());
			st.setInt(2, obj.getProduct().getProductId());
			st.setInt(3, obj.getProductQuantity());
			st.setDouble(4, obj.getProductPrice());
			st.setInt(5, obj.getCartItemId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Integer id) {
		String sql = "DELETE FROM CartItems WHERE cartItemId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public CartItems findById(Integer id) {
		String sql = "SELECT ci.cartItemId, ci.productQuantity, ci.productPrice, " + "p.productId, p.productName, "
				+ "c.cartId, c.totalValue " + "FROM CartItems ci "
				+ "LEFT JOIN Product p ON ci.productId = p.productId " + "LEFT JOIN Cart c ON ci.cartId = c.cartId "
				+ "WHERE ci.cartItemId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				Product product = instantiateProduct(rs);
				Cart cart = instantiateCart(rs);
				CartItems item = instantiateCartItem(rs, cart, product);
				return item;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<CartItems> findAll() {
		String sql = "SELECT ci.cartItemId, ci.productQuantity, ci.productPrice, " + "p.productId, p.productName, "
				+ "c.cartId, c.totalValue " + "FROM CartItems ci "
				+ "LEFT JOIN Product p ON ci.productId = p.productId " + "LEFT JOIN Cart c ON ci.cartId = c.cartId";

		try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
			List<CartItems> list = new ArrayList<>();
			while (rs.next()) {
				Product product = instantiateProduct(rs);
				Cart cart = instantiateCart(rs);
				CartItems item = instantiateCartItem(rs, cart, product);
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

}
