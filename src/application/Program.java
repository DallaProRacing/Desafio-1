package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import Conection.DB;
import Conection.DbException;
import entity.Customer;
import implementation.CustomerDaoJBDC;

public class Program {

	public static void main(String[] args) {
		Connection conn = null;
        try {
            conn = DB.getConnection(); 
            CustomerDaoJBDC customerDao = new CustomerDaoJBDC(conn);

           
            System.out.println("=== Inserir Novo Cliente ===");
            Customer newCustomer = new Customer();
            newCustomer.setCustomerName("João Silva");
            newCustomer.setAddress("Rua das Flores, 123");
            newCustomer.setContact("123456789");
            newCustomer.setBirthDate(java.sql.Date.valueOf("1990-01-01")); 
            customerDao.insert(newCustomer);
            System.out.println("Cliente inserido com ID: " + newCustomer.getCustomerId());

          
            System.out.println("=== Atualizar Cliente ===");
            newCustomer.setCustomerName("João Silva Atualizado");
            customerDao.update(newCustomer);
            System.out.println("Cliente atualizado.");

            
            System.out.println("=== Encontrar Cliente pelo ID ===");
            Customer foundCustomer = customerDao.findById(newCustomer.getCustomerId());
            if (foundCustomer != null) {
                System.out.println("Cliente encontrado: " + foundCustomer.getCustomerName());
            } else {
                System.out.println("Cliente não encontrado.");
            }

           
            System.out.println("=== Listar Todos os Clientes ===");
            List<Customer> allCustomers = customerDao.findAll();
            for (Customer customer : allCustomers) {
                System.out.println("Cliente ID: " + customer.getCustomerId() + ", Nome: " + customer.getCustomerName());
            }

            System.out.println("=== Excluir Cliente pelo ID ===");
            customerDao.deleteById(newCustomer.getCustomerId());
            System.out.println("Cliente excluído.");
            
        } catch (DbException e) {
            System.out.println("Erro: " + e.getMessage());
        
        }
    }
}
