-- Create Database
CREATE DATABASE ShoppingCartDB;
USE ShoppingCartDB;

-- Table Customer
CREATE TABLE Customer (
    customerId INT AUTO_INCREMENT PRIMARY KEY,
    customerName VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    contact VARCHAR(20),
    birthDate DATE
);

-- Table Category
CREATE TABLE Category (
    categoryId INT AUTO_INCREMENT PRIMARY KEY,
    categoryName VARCHAR(100) NOT NULL
);

-- Table Product
CREATE TABLE Product (
    productId INT AUTO_INCREMENT PRIMARY KEY,
    productName VARCHAR(100) NOT NULL,
    productQuantity INT NOT NULL DEFAULT 0,  -- Adicionado valor padrão
    productPrice DECIMAL(10, 2) NOT NULL,
    categoryId INT,
    FOREIGN KEY (categoryId) REFERENCES Category(categoryId)
);

-- Table Cart
CREATE TABLE Cart (
    cartId INT AUTO_INCREMENT PRIMARY KEY,
    customerId INT NOT NULL,
    totalValue DECIMAL(10, 2) DEFAULT 0.00,
    FOREIGN KEY (customerId) REFERENCES Customer(customerId)
);

-- Table CartItems (Items in Cart)
CREATE TABLE CartItems (
    cartItemId INT AUTO_INCREMENT PRIMARY KEY,
    cartId INT NOT NULL,
    productId INT NOT NULL,
    productQuantity INT NOT NULL,
    productPrice DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cartId) REFERENCES Cart(cartId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);

-- Table Sale
CREATE TABLE Sale (
    saleId INT AUTO_INCREMENT PRIMARY KEY,
    cartId INT NOT NULL,
    customerId INT NOT NULL,
    productPrice DECIMAL(10, 2) NOT NULL,   
    saleValue DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cartId) REFERENCES Cart(cartId),
    FOREIGN KEY (customerId) REFERENCES Customer(customerId)
);

-- Initial Data for Category
INSERT INTO Category (categoryName) VALUES ('Electronics'), ('Clothing'), ('Food');

-- Initial Data for Product
INSERT INTO Product (productName, productQuantity, productPrice, categoryId)
VALUES 
    ('Smartphone', 50, 1200.00, 1),
    ('T-Shirt', 200, 50.00, 2),
    ('Chocolate', 300, 5.00, 3);


-- Initial Data for Customer
INSERT INTO Customer (customerName, address, contact, birthDate)
VALUES 
    ('John Silva', '123 Flower St', '123456789', '1990-05-10'),
    ('Maria Oliveira', '456 Central Ave', '987654321', '1985-10-20');



