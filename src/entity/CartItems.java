package entity;

import java.io.Serializable;

public class CartItems implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer cartItemId; // Adicionando o ID do item do carrinho
    private Cart cart; // O carrinho associado ao item
    private Product product; // O produto associado ao item
    private int productQuantity; // Quantidade do produto no carrinho
    private double productPrice; // Preço do produto

    public CartItems() {
    }

    // Método para obter o ID do item do carrinho
    public Integer getCartItemId() {
        return cartItemId;
    }

    // Método para definir o ID do item do carrinho
    public void setCartItemId(Integer cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart; // Adicionando o método setCart
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.productPrice = product.getProductPrice(); // Atualiza o preço automaticamente
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
