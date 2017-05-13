package wrappers;

import java.math.BigDecimal;

import entities.core.Shopping;
import entities.core.ShoppingState;

public class ShoppingWrapper {

    private int id;

    private int amount;

    private int discount;

    private String productCode;

    private String description;

    private BigDecimal retailPrice;

    private ShoppingState shoppingState;

    public ShoppingWrapper() {
    }

    public ShoppingWrapper(Shopping shopping) {
        this.id = shopping.getId();
        this.amount = shopping.getAmount();
        this.discount = shopping.getDiscount();
        this.productCode = shopping.getProduct().getCode();
        this.description = shopping.getDescription();
        this.retailPrice = shopping.getRetailPrice();
        this.shoppingState = shopping.getShoppingState();
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public int getDiscount() {
        return discount;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public ShoppingState getShoppingState() {
        return shoppingState;
    }

    @Override
    public String toString() {
        return "ShoppingWrapper [id=" + id + ", amount=" + amount + ", discount=" + discount + ", productCode=" + productCode
                + ", description=" + description + ", retailPrice=" + retailPrice + ", shoppingState=" + shoppingState + "]";
    }

}
