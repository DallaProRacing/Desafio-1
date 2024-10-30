package implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Inventory;
import entity.Product;
import model.InventoryDao;

public class InventoryDaoJDBC implements InventoryDao {
	private Connection conn;

	public InventoryDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	private Inventory instantiateInventory(ResultSet rs, Product product) throws SQLException {
		Inventory inventory = new Inventory();
		inventory.setInventoryId(rs.getInt("inventoryId"));
		inventory.setProduct(product);
		inventory.setQuantity(rs.getInt("quantity"));
		return inventory;
	}

	private Product instantiateProduct(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setProductId(rs.getInt("productId"));
		product.setProductName(rs.getString("productName"));
		return product;
	}

	@Override
	public void insert(Inventory obj) {
		String sql = "INSERT INTO Inventory (productId, quantity) VALUES (?, ?)";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, obj.getProduct().getProductId());
			st.setInt(2, obj.getQuantity());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void update(Inventory obj) {
		String sql = "UPDATE Inventory SET productId = ?, quantity = ? WHERE inventoryId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, obj.getProduct().getProductId());
			st.setInt(2, obj.getQuantity());
			st.setInt(3, obj.getInventoryId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Integer id) {
		String sql = "DELETE FROM Inventory WHERE inventoryId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	

	@Override
	public Inventory findById(Integer id) {
		String sql = "SELECT i.inventoryId, i.quantity, " + "p.productId, p.productName " + "FROM Inventory i "
				+ "LEFT JOIN Product p ON i.productId = p.productId " + "WHERE i.inventoryId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				Product product = instantiateProduct(rs);
				Inventory inventory = instantiateInventory(rs, product);
				return inventory;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<Inventory> findAll() {
		String sql = "SELECT i.inventoryId, i.quantity, " + "p.productId, p.productName " + "FROM Inventory i "
				+ "LEFT JOIN Product p ON i.productId = p.productId";

		try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

			List<Inventory> list = new ArrayList<>();
			while (rs.next()) {
				Product product = instantiateProduct(rs);
				Inventory inventory = instantiateInventory(rs, product);
				list.add(inventory);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}


