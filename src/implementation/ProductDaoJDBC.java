package implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Category;
import entity.Product;
import model.ProductDao;

public class ProductDaoJDBC implements ProductDao {
	private Connection conn;

	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	private Product instantiateProduct(ResultSet rs, Category category) throws SQLException {
		Product product = new Product();
		product.setProductId(rs.getInt("productId"));
		product.setProductName(rs.getString("productName"));
		product.setProductQuantity(rs.getInt("productQuantity"));
		product.setProductPrice(rs.getDouble("productPrice"));
		product.setCategory(category);
		return product;
	}

	private Category instantiateCategory(ResultSet rs) throws SQLException {
		Category category = new Category();
		category.setCategoryId(rs.getInt("categoryId"));
		category.setCategoryName(rs.getString("categoryName"));
		return category;
	}

	@Override
	public void insert(Product obj) {
		String sql = "INSERT INTO Product (productName, productQuantity, productPrice, categoryId) VALUES (?, ?, ?, ?)";
	    
	    try (PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
	        st.setString(1, obj.getProductName());
	        st.setInt(2, obj.getProductQuantity());
	        st.setDouble(3, obj.getProductPrice());
	        st.setInt(4, obj.getCategory().getCategoryId());
	        
	        int rowsAffected = st.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            try (ResultSet rs = st.getGeneratedKeys()) {
	                if (rs.next()) {
	                    obj.setProductId(rs.getInt(1));
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
	public void update(Product obj) {
		String sql = "UPDATE Product SET productName = ?, productQuantity = ?, productPrice = ?, categoryId = ? WHERE productId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setString(1, obj.getProductName());
			st.setInt(2, obj.getProductQuantity());
			st.setDouble(3, obj.getProductPrice());
			st.setInt(4, obj.getCategory().getCategoryId());
			st.setInt(5, obj.getProductId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Integer id) {
		String sql = "DELETE FROM Product WHERE productId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Product findById(Integer id) {
		String sql = "SELECT p.productId, p.productName, p.productQuantity, p.productPrice, "
				+ "c.categoryId, c.categoryName " + "FROM Product p "
				+ "LEFT JOIN Category c ON p.categoryId = c.categoryId " + "WHERE p.productId = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				Category category = instantiateCategory(rs);
				Product product = instantiateProduct(rs, category);
				return product;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<Product> findAll() {
		String sql = "SELECT p.productId, p.productName, p.productQuantity, p.productPrice, "
				+ "c.categoryId, c.categoryName " + "FROM Product p "
				+ "LEFT JOIN Category c ON p.categoryId = c.categoryId";

		try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

			List<Product> list = new ArrayList<>();
			while (rs.next()) {
				Category category = instantiateCategory(rs);
				Product product = instantiateProduct(rs, category);
				list.add(product);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
