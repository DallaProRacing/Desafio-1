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
import implementation.CartDaoJDBC;
import implementation.CustomerDaoJBDC;
import implementation.ProductDaoJDBC;
import implementation.SaleDaoJDBC;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;

        try {
            conn = DB.getConnection();
            CustomerDaoJBDC customerDao = new CustomerDaoJBDC(conn);
            ProductDaoJDBC productDao = new ProductDaoJDBC(conn);
            CartDaoJDBC cartDao = new CartDaoJDBC(conn);
            SaleDaoJDBC saleDao = new SaleDaoJDBC(conn);

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
            java.util.Date utilBirthDate;

            try {
                utilBirthDate = sdf.parse(birthDateStr);
            } catch (ParseException e) {
                System.out.println("Data inválida. O formato correto é dd/MM/yyyy.");
                return;
            }

            Date sqlBirthDate = new Date(utilBirthDate.getTime());
            customer.setBirthDate(sqlBirthDate);
            customerDao.insert(customer);

            // 2. Criar e inserir o carrinho temporário
            Cart cart = new Cart();
            cart.setCustomer(customer);
            cart.setTotalValue(0.0);
            cartDao.insert(cart); // Insere o carrinho e define o cartId

            double totalValue = 0.00;

            boolean shopping = true;
            while (shopping) {
                System.out.print("Enter the product ID you want to add to the cart: ");
                int productId = Integer.parseInt(scanner.nextLine());

                Product product = productDao.findById(productId);
                if (product != null) {
                    System.out.print("How many items of product " + product.getProductName() + " do you want to add? ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    if (quantity > product.getProductQuantity()) {
                        System.out.println("Not enough stock available. Available quantity: " + product.getProductQuantity());
                        continue;
                    }

                    // Adiciona o item na tabela CartItems
                    CartItems cartItem = new CartItems();
                    cartItem.setCart(cart);
                    cartItem.setProduct(product);
                    cartItem.setProductQuantity(quantity);
                    cartItem.setProductPrice(product.getProductPrice());
                    
                    double itemTotal = quantity * product.getProductPrice();
                    totalValue += itemTotal; // Atualiza o valor total do carrinho

                    // Inserir o item na tabela CartItems
                    cartDao.inserir(cartItem);

                    // Atualiza o estoque do produto
                    product.setProductQuantity(product.getProductQuantity() - quantity);
                    productDao.update(product);

                    System.out.println("Item added to the cart!");
                    System.out.printf("Current total cart value: $%.2f%n", totalValue);
                } else {
                    System.out.println("Product not found.");
                }

                System.out.print("Do you want to add more products? (y/n): ");
                String continueShopping = scanner.nextLine();
                if (!continueShopping.equalsIgnoreCase("y")) {
                    shopping = false;
                }
            }

            // 3. Atualizar o valor total do carrinho e finalizar a compra
            cart.setTotalValue(totalValue);
            cartDao.update(cart); // Atualiza o carrinho com o valor total final

            saleDao.finalizeSale(cart, customer);
            System.out.println("Purchase completed successfully!");

        } finally {
            scanner.close();
            if (conn != null) {
                DB.closeConnection();
            }
        }
    }
}
