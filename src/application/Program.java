package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import Conection.DB;

public class Program {

	public static void main(String[] args) {		
	
	 Connection conn = null;
     Statement st = null;
     ResultSet rs = null;

     try {        
         conn = DB.getConnection();
         System.out.println("Conexão bem-sucedida!");
     
         st = conn.createStatement();
         rs = st.executeQuery("SELECT * FROM Customer");
        
         while (rs.next()) {
             System.out.println("ID: " + rs.getInt("customerId") + 
                                ", Name: " + rs.getString("customerName"));
         }
     } 
     catch (Exception e) {
         e.printStackTrace();
     } 
     finally {
      
         DB.closeResultSet(rs);
         DB.closeStatement(st);
         DB.closeConnection();
     }
 }
}
