package application;

import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import Conection.DB;
import entity.Cart;
import entity.CartItems;
import entity.Customer;
import entity.Product;
import entity.Sale; // Importar a classe Sale
import implementation.CartDaoJDBC;
import implementation.CustomerDaoJBDC;
import implementation.ProductDaoJDBC;
import implementation.SaleDaoJDBC; // Importar a classe SaleDaoJDBC

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;

        try {
            conn = DB.getConnection();
            // DAO instances
            CustomerDaoJBDC customerDao = new CustomerDaoJBDC(conn);
            ProductDaoJDBC productDao = new ProductDaoJDBC(conn);
            CartDaoJDBC cartDao = new CartDaoJDBC(conn);
            SaleDaoJDBC saleDao = new SaleDaoJDBC(conn); // Instanciar SaleDaoJDBC

            // 1. Inserir dados do cliente
            Customer customer = new Customer();

            System.out.print("Enter the customer's name: ");
            String customerName = scanner.nextLine();
            customer.setCustomerName(customerName);

            System.out.print("Enter the customer's address: ");
            String address = scanner.nextLine();
            customer.setAddress(address);

            System.out.print("Enter the customer's contact: ");
            String contact = scanner.nextLine();
            customer.setContact(contact);

            System.out.println("Informe a data de nascimento do cliente (dd/MM/yyyy):");
            String birthDateStr = scanner.nextLine();

            // Formatar a data
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilBirthDate = null;

            try {
                utilBirthDate = sdf.parse(birthDateStr); // Formata a string da data
            } catch (ParseException e) {
                System.out.println("Data inválida. O formato correto é dd/MM/yyyy.");
                return; // Encerra o programa se a data for inválida
            }

            // Converte para java.sql.Date
            Date sqlBirthDate = new Date(utilBirthDate.getTime());

            // Cria o cliente e salva no banco
            customer.setBirthDate(sqlBirthDate); // Define a data formatada como java.sql.Date
            customerDao.insert(customer);

            // 2. Criar um novo carrinho para o cliente
            Cart cart = new Cart();
            cart.setCustomer(customer);
            cartDao.insert(cart); // Salva o carrinho no banco

            // Loop para adicionar produtos ao carrinho
            boolean shopping = true;
            while (shopping) {
                System.out.print("Enter the product ID you want to add to the cart: ");
                int productId = Integer.parseInt(scanner.nextLine());

                Product product = productDao.findById(productId); // Busca o produto pelo ID
                if (product != null) {
                    System.out.print("How many items of product " + product.getProductName() + " do you want to add? ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    // Verifica se a quantidade desejada está disponível
                    if (quantity > product.getProductQuantity()) {
                        System.out.println("Not enough stock available. Available quantity: " + product.getProductQuantity());
                        continue;
                    }

                    // Adiciona o item ao carrinho
                    CartItems cartItem = new CartItems();
                    cartItem.setCart(cart);
                    cartItem.setProduct(product);
                    cartItem.setProductQuantity(quantity);
                    cartItem.setProductPrice(product.getProductPrice());

                    // Salvar o item no carrinho (implemente este método no CartDaoJDBC)
                    cartDao.insert(cart); // Método que você precisa implementar

                    // Atualiza a quantidade do produto em estoque
                    product.setProductQuantity(product.getProductQuantity() - quantity);
                    productDao.update(product); // Atualiza o estoque

                    System.out.println("Item added to the cart!");
                } else {
                    System.out.println("Product not found.");
                }

                System.out.print("Do you want to add more products? (y/n): ");
                String continueShopping = scanner.nextLine();
                if (!continueShopping.equalsIgnoreCase("y")) {
                    shopping = false;
                }
            }

            // 3. Finalizar a compra e inserir na tabela Sale
            saleDao.finalizeSale(cart, customer); // Chamando o método finalizeSale

            System.out.println("Purchase completed successfully!");

        } finally {
            scanner.close();
            if (conn != null) {
                DB.closeConnection();
            }
        }
    }
}
