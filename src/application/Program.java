package application;

import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import Conection.DB;
import entity.Cart;
import entity.CartItems;
import entity.Category;
import entity.Customer;
import entity.Product;
import implementation.CartDaoJDBC;
import implementation.CategoryDaoJDBC;
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
            CategoryDaoJDBC categoryDao = new CategoryDaoJDBC(conn);

            System.out.print("Are you a Customer or an Employee? (C/E): ");
            String userType = scanner.nextLine();

            if (userType.equalsIgnoreCase("E")) {
                // Menu para funcionários
                boolean employeeMenu = true;
                while (employeeMenu) {
                    System.out.print("Do you want to update an existing product or add a new one? (Update/Add): ");
                    String action = scanner.nextLine();

                    if (action.equalsIgnoreCase("Add")) {
                        // Adicionar novo produto
                        System.out.print("Do you want to add a new category? (y/n): ");
                        String addCategoryResponse = scanner.nextLine();

                        int categoryId;
                        if (addCategoryResponse.equalsIgnoreCase("y")) {
                            // Adicionar nova categoria
                            Category category = new Category();
                            System.out.print("Enter the category name: ");
                            String categoryName = scanner.nextLine();
                            category.setCategoryName(categoryName);
                            categoryDao.insert(category); // Insere a nova categoria no banco

                            // Obtém o categoryId da nova categoria pelo nome
                            categoryId = category.getCategoryId();
                            System.out.println("Category added successfully!");
                        } else {
                            System.out.print("Enter the category ID for this product: ");
                            categoryId = Integer.parseInt(scanner.nextLine());
                        }

                        Product product = new Product();
                        product.setCategoryId(categoryId); // Definir categoryId para o produto

                        // Se `Product` possui uma referência `Category`, defina-a explicitamente
                        Category category = categoryDao.findById(categoryId);
                        if (category != null) {
                            product.setCategory(category); // Definir a categoria completa, se necessário
                        }

                        System.out.print("Enter the product name: ");
                        product.setProductName(scanner.nextLine());

                        System.out.print("Enter the product price: ");
                        product.setProductPrice(Double.parseDouble(scanner.nextLine()));

                        System.out.print("Enter the product quantity: ");
                        int quantity = Integer.parseInt(scanner.nextLine());
                        product.setProductQuantity(quantity); // Definir a quantidade no produto

                        // Insere o produto no banco
                        productDao.insert(product);
                        System.out.println("Product added successfully!");
                    }

                    System.out.print("Do you want to perform another action? (y/n): ");
                    String continueEmployee = scanner.nextLine();
                    if (!continueEmployee.equalsIgnoreCase("y")) {
                        employeeMenu = false;
                    }
                }
            } else if (userType.equalsIgnoreCase("C")) {
                // Funcionalidade de cliente
                System.out.print("Are you already registered? (y/n): ");
                String isRegistered = scanner.nextLine();

                Customer customer;
                if (isRegistered.equalsIgnoreCase("y")) {
                    System.out.print("Enter your customer ID: ");
                    int customerId = Integer.parseInt(scanner.nextLine());
                    customer = customerDao.findById(customerId);

                    if (customer == null) {
                        System.out.println("Customer not found. Please register.");
                        return;
                    }
                } else {
                    // Registro do cliente
                    customer = new Customer();

                    System.out.print("Enter your name: ");
                    customer.setCustomerName(scanner.nextLine());

                    System.out.print("Enter your address: ");
                    customer.setAddress(scanner.nextLine());

                    System.out.print("Enter your contact: ");
                    customer.setContact(scanner.nextLine());

                    System.out.print("Enter your birth date (dd/MM/yyyy): ");
                    String birthDateStr = scanner.nextLine();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date utilBirthDate;
                    try {
                        utilBirthDate = sdf.parse(birthDateStr);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Use dd/MM/yyyy.");
                        return;
                    }

                    Date sqlBirthDate = new Date(utilBirthDate.getTime());
                    customer.setBirthDate(sqlBirthDate);
                    customerDao.insert(customer);
                }

                // Processo de criação do carrinho
                Cart cart = new Cart();
                cart.setCustomer(customer);
                cart.setTotalValue(0.0);
                cartDao.insert(cart);

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

                        CartItems cartItem = new CartItems();
                        cartItem.setCart(cart);
                        cartItem.setProduct(product);
                        cartItem.setProductQuantity(quantity);
                        cartItem.setProductPrice(product.getProductPrice());

                        double itemTotal = quantity * product.getProductPrice();
                        totalValue += itemTotal;

                        cartDao.inserir(cartItem);

                        int newProductQuantity = product.getProductQuantity() - quantity;
                        product.setProductQuantity(newProductQuantity); // Atualizar o objeto Product localmente
                        productDao.update(product); // Persistir a atualização na tabela Product

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

                // Aplicar desconto opcional
                System.out.print("Do you want to apply a 10% discount? (y/n): ");
                String applyDiscount = scanner.nextLine();
                if (applyDiscount.equalsIgnoreCase("y")) {
                    totalValue *= 0.9;
                }

                cart.setTotalValue(totalValue);
                cartDao.update(cart);
                saleDao.finalizeSale(cart, customer);

                System.out.println("Purchase completed successfully!");
                System.out.println("Customer: " + customer.getCustomerName());
                System.out.printf("Total cart value (after discount, if applied): $%.2f%n", totalValue);
            }

        } finally {
            scanner.close();
            if (conn != null) {
                DB.closeConnection();
            }
        }
    }
}
