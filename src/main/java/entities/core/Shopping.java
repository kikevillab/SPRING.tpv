package entities.core;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Shopping {
    @Id
    @GeneratedValue
    private int id;

    private int amount;

    private int discount;

    @ManyToOne
    @JoinColumn
    private Product product;

    private String description;

    private BigDecimal retailPrice;

    @Enumerated(EnumType.STRING)
    private ShoppingState shoppingState;

    public Shopping() {
    }

    public Shopping(int amount, int discount, Product product, String description, BigDecimal retailPrice, ShoppingState shoppingState) {
        this.amount = amount;
        this.discount = discount;
        this.product = product;
        this.description = description;
        this.retailPrice = retailPrice;
        this.shoppingState = shoppingState;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public ShoppingState getShoppingState() {
        return shoppingState;
    }

    public void setShoppingState(ShoppingState shoppingState) {
        this.shoppingState = shoppingState;
    }

    public double getShoppingTotal() {
        float discountDecimal = (float) discount / 100;
        double shoppingSubtotal = getShoppingSubtotal();
        return shoppingSubtotal - (shoppingSubtotal * discountDecimal);
    }
    
    public double getShoppingSubtotal(){
        double retailPriceDouble = retailPrice.doubleValue();
        return amount * retailPriceDouble;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id == ((Shopping) obj).id;
    }

    @Override
    public String toString() {
        return "Shopping[" + id + ": amount=" + amount + ", discount=" + discount + ", productId=" + product + ", description="
                + description + ", retailPrice=" + retailPrice + ", shoppingState=" + shoppingState + "]";
    }

}
