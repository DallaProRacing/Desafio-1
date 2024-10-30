package implementation;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Conection.DB;
import Conection.DbException;
import entity.Cart;
import entity.Customer;
import model.CustomerDao;


public class CustomerDaoJBDC implements CustomerDao {

	private Connection conn;

	public CustomerDaoJBDC(Connection conn) {
		this.conn = conn;
	}

	
	private Customer instantiateCustomer(ResultSet rs, Cart cart) throws SQLException {
		Customer obj = new Customer();
		obj.setCustomerId(rs.getInt("customerId"));
		obj.setCustomerName(rs.getString("customerName"));
		obj.setAddress(rs.getString("address"));
		obj.setContact(rs.getString("contact"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setCart(cart);
		return obj;
	}
	
	private Cart instantiateCart(ResultSet rs) throws SQLException {
		Cart cart = new Cart();
		cart.setCartId(rs.getInt("DepartmentId"));
		cart.setTotalValue(rs.getDouble("totalValue"));
		return cart;
	}

	@Override
	public void insert(Customer obj) {
		 PreparedStatement st = null;
		    try {
		        st = conn.prepareStatement(
		            "INSERT INTO Customer " +
		            "(customerName, address, contact, birthDate) " +
		            "VALUES (?, ?, ?, ?)", 
		            Statement.RETURN_GENERATED_KEYS);

		        st.setString(1, obj.getCustomerName());
		        st.setString(2, obj.getAddress());
		        st.setString(3, obj.getContact());
		        st.setDate(4, new java.sql.Date(obj.getBirthDate().getTime())); 

		        int rowsAffected = st.executeUpdate();
		        
		        if (rowsAffected > 0) {
		            ResultSet rs = st.getGeneratedKeys();
		            if (rs.next()) {
		                int id = rs.getInt(1);
		                obj.setCustomerId(id); 
		            }
		            rs.close();
		        } else {
		            throw new DbException("Unexpected error! No rows affected!");
		        }
		    } catch (SQLException e) {
		        throw new DbException(e.getMessage());
		    } finally {
		        DB.closeStatement(st);
		    }
		
		
	}

	@Override
	public void update(Customer obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE Customer "
					+ "SET customerName = ?, address = ?, contact = ?, birthDate = ? " + "WHERE customerId = ?");

			st.setString(1, obj.getCustomerName());
			st.setString(2, obj.getAddress());
			st.setString(3, obj.getContact());
			st.setDate(4, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setInt(5, obj.getCustomerId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM Customer WHERE customerId = ?");

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Customer findById(Integer id) {
		PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        st = conn.prepareStatement("SELECT * FROM Customer WHERE customerId = ?");
	        st.setInt(1, id);
	        rs = st.executeQuery();

	        if (rs.next()) {
	           Cart cart = new Cart(); 
	           Customer obj = instantiateCustomer(rs, cart);
	            return obj;
	        }
	        return null;
	    } catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}

	@Override
	public List<Customer> findAll() {
		 PreparedStatement st = null;
		    ResultSet rs = null;
		    try {
		        st = conn.prepareStatement("SELECT * FROM Customer ORDER BY customerName");
		        rs = st.executeQuery();

		        List<Customer> list = new ArrayList<>();

		        while (rs.next()) {
		           
		        	Cart cart = new Cart(); 
		        	Customer obj = instantiateCustomer(rs, cart);
		            list.add(obj);
		        }
		        return list;
		    } catch (SQLException e) {
		        throw new DbException(e.getMessage());
		    } finally {
		        DB.closeStatement(st);
		        DB.closeResultSet(rs);
		    }
	}
}
