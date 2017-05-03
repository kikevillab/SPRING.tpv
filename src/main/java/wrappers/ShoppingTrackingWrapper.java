package wrappers;

import entities.core.Shopping;
import entities.core.ShoppingState;

public class ShoppingTrackingWrapper {
    private String productCode;

    private String description;

    private ShoppingState shoppingState;

    public ShoppingTrackingWrapper() {
        super();
    }

    public ShoppingTrackingWrapper(Shopping shopping) {
        super();
        this.productCode = shopping.getProduct().getCode();
        this.description = shopping.getDescription();
        this.shoppingState = shopping.getShoppingState();
    }

    public String getProductCode() {
        return productCode;
    }

    public String getDescription() {
        return description;
    }

    public ShoppingState getShoppingState() {
        return shoppingState;
    }

    @Override
    public String toString() {
        return "ShoppingTrackingWrapper [productCode=" + productCode + ", description=" + description + ", shoppingState=" + shoppingState
                + "]";
    }
}
