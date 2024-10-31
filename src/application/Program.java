package application;

import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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

            String userType = "";
            while (true) {
                System.out.print("Are you a Customer or an Employee? (C/E): ");
                userType = scanner.nextLine();
                if (userType.equalsIgnoreCase("C") || userType.equalsIgnoreCase("E")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'C' for Customer or 'E' for Employee.");
                }
            }

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

                        int categoryId = 0;
                        if (addCategoryResponse.equalsIgnoreCase("y")) {
                            // Adicionar nova categoria
                            Category category = new Category();
                            System.out.print("Enter the category name: ");
                            category.setCategoryName(scanner.nextLine());
                            categoryDao.insert(category); // Insere a nova categoria no banco

                            // Obtém o categoryId da nova categoria pelo nome
                            categoryId = category.getCategoryId();
                            System.out.println("Category added successfully!");
                        } else {
                            while (true) {
                                System.out.print("Enter the category ID for this product: ");
                                try {
                                    categoryId = Integer.parseInt(scanner.nextLine());
                                    break; // Sai do loop se a entrada for válida
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid category ID (integer).");
                                }
                            }
                        }

                        Product product = new Product();
                        product.setCategoryId(categoryId); // Definir categoryId para o produto

                        System.out.print("Enter the product name: ");
                        product.setProductName(scanner.nextLine());

                        double productPrice = 0;
                        while (true) {
                            System.out.print("Enter the product price: ");
                            try {
                                productPrice = Double.parseDouble(scanner.nextLine());
                                product.setProductPrice(productPrice);
                                break; // Sai do loop se a entrada for válida
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a valid price (number).");
                            }
                        }

                        int quantity = 0;
                        while (true) {
                            System.out.print("Enter the product quantity: ");
                            try {
                                quantity = Integer.parseInt(scanner.nextLine());
                                product.setProductQuantity(quantity); // Definir a quantidade no produto
                                break; // Sai do loop se a entrada for válida
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a valid quantity (integer).");
                            }
                        }

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
                    int customerId = 0;
                    while (true) {
                        System.out.print("Enter your customer ID: ");
                        try {
                            customerId = Integer.parseInt(scanner.nextLine());
                            customer = customerDao.findById(customerId);

                            if (customer == null) {
                                System.out.println("Customer not found. Please register.");
                                return;
                            }
                            break; // Sai do loop se a entrada for válida
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid customer ID (integer).");
                        }
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

                    Date sqlBirthDate = null;
                    while (true) {
                        System.out.print("Enter your birth date (dd/MM/yyyy): ");
                        String birthDateStr = scanner.nextLine();

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date utilBirthDate;
                        try {
                            utilBirthDate = sdf.parse(birthDateStr);
                            sqlBirthDate = new Date(utilBirthDate.getTime());
                            break; // Sai do loop se a entrada for válida
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Use dd/MM/yyyy.");
                        }
                    }
                    customer.setBirthDate(sqlBirthDate);
                    customerDao.insert(customer);
                }

                // Processo de criação do carrinho
                Cart cart = new Cart();
                cart.setCustomer(customer);
                cart.setTotalValue(0.0);
                cartDao.insert(cart);

                double totalValue = 0.00;
                List<CartItems> purchasedItems = new ArrayList<>(); // Lista para armazenar itens comprados
                boolean shopping = true;
                while (shopping) {
                    int productId = 0;
                    while (true) {
                        System.out.print("Enter the product ID you want to add to the cart: ");
                        try {
                            productId = Integer.parseInt(scanner.nextLine());
                            Product product = productDao.findById(productId);
                            if (product != null) {
                                System.out.print("How many items of product " + product.getProductName()
                                        + " do you want to add? ");
                                int quantity = Integer.parseInt(scanner.nextLine());

                                if (quantity > product.getProductQuantity()) {
                                    System.out.println("Not enough stock available. Available quantity: "
                                            + product.getProductQuantity());
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
                                purchasedItems.add(cartItem); // Adiciona o item à lista de itens comprados

                                int newProductQuantity = product.getProductQuantity() - quantity;
                                product.setProductQuantity(newProductQuantity); // Atualizar o objeto Product localmente
                                productDao.update(product); // Persistir a atualização na tabela Product

                                System.out.println("Item added to the cart!");
                                System.out.printf("Current total cart value: $%.2f%n", totalValue);
                            } else {
                                System.out.println("Product not found.");
                            }
                            break; // Sai do loop se a entrada for válida
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid product ID (integer).");
                        }
                    }

                    System.out.print("Do you want to add another productC? (y/n): ");
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

                // Exibir resumo da compra
                System.out.println("Purchase completed successfully!");
                System.out.println("Customer: " + customer.getCustomerName());
                System.out.println("Items purchased:");
                for (CartItems item : purchasedItems) {
                    System.out.printf("- %s, Quantity: %d, Price: $%.2f%n",
                            item.getProduct().getProductName(), item.getProductQuantity(), item.getProductPrice());
                }
                System.out.printf("Total value: $%.2f%n", cart.getTotalValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeConnection();
            scanner.close();
        }
    }
}
