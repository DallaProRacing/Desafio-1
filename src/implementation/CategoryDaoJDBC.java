package implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Category;
import model.CategoryDao;

public class CategoryDaoJDBC implements CategoryDao {
	private Connection conn;

	public CategoryDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Category obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                "INSERT INTO Category (categoryName) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getCategoryName());
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        obj.setCategoryId(id);  // Define o categoryId no objeto
                    }
                }
            } else {
                throw new SQLException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
	}

	@Override
	public void update(Category obj) {
		String sql = "UPDATE Category SET categoryName = ? WHERE categoryId = ?";
		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setString(1, obj.getCategoryName());
			st.setInt(2, obj.getCategoryId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Integer id) {
		String sql = "DELETE FROM Category WHERE categoryId = ?";
		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Category findById(Integer id) {
		String sql = "SELECT * FROM Category WHERE categoryId = ?";
		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				Category obj = new Category();
				obj.setCategoryId(rs.getInt("categoryId"));
				obj.setCategoryName(rs.getString("categoryName"));
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<Category> findAll() {
		String sql = "SELECT * FROM Category";
		try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
			List<Category> list = new ArrayList<>();
			while (rs.next()) {
				Category obj = new Category();
				obj.setCategoryId(rs.getInt("categoryId"));
				obj.setCategoryName(rs.getString("categoryName"));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public int findCategoryIdByName(String categoryName) {
		String sql = "SELECT categoryId FROM Category WHERE categoryName = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, categoryName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("categoryId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
