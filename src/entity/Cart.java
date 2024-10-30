package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer cartId;
    private Integer customerId;
    private Double totalValue; // Pode ser inicializado no cálculo

    private Customer customer;
    private List<CartItems> items = new ArrayList<>(); // Lista de itens do carrinho

    public Cart() {
        this.totalValue = 0.0; // Inicializa totalValue como 0.0
    }

    public Cart(Integer cartId, Integer customerId, Customer customer) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.customer = customer;
        this.items = new ArrayList<>(); // Inicializa a lista de itens
        this.totalValue = 0.0; // Inicializa totalValue como 0.0
    }

    public void addItem(CartItems item) {
        items.add(item);
        calculateTotalValue(); // Calcula o total sempre que um item é adicionado
    }

    public void calculateTotalValue() {
        totalValue = 0.0; // Reseta o total antes de recalcular
        for (CartItems item : items) {
            totalValue += item.getProductPrice() * item.getProductQuantity(); // Atualiza totalValue
        }
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) { // Método setTotalValue adicionado
        this.totalValue = totalValue;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CartItems> getItems() {
        return items; // Permite acesso à lista de itens
    }

    @Override
    public String toString() {
        return "Cart [cartId=" + cartId + ", customerId=" + customerId + ", totalValue=" + totalValue + ", customer="
                + customer + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, customer, customerId, totalValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cart other = (Cart) obj;
        return Objects.equals(cartId, other.cartId) && Objects.equals(customer, other.customer)
                && Objects.equals(customerId, other.customerId) && Objects.equals(totalValue, other.totalValue);
    }
}
