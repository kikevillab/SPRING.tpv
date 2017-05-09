package wrappers;

import entities.core.ShoppingState;

public class ShoppingUpdateWrapper {

    private String productCode;

    private int amount;

    private ShoppingState shoppingState;

    public ShoppingUpdateWrapper() {
    }

    public ShoppingUpdateWrapper(String productCode, int amount, ShoppingState shoppingState) {
        super();
        this.productCode = productCode;
        this.amount = amount;
        this.shoppingState = shoppingState;
    }



    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ShoppingState getShoppingState() {
        return shoppingState;
    }

    public void setShoppingState(ShoppingState shoppingState) {
        this.shoppingState = shoppingState;
    }

    @Override
    public String toString() {
        return "ShoppingUpdateWrapper [productCode=" + productCode + ", amount=" + amount + ", shoppingState=" + shoppingState + "]";
    }

}
