package wrappers;

import java.util.List;

public class TicketCreationWrapper {

    private Integer userId;

    private List<ShoppingCreationWrapper> shoppingList;

    public TicketCreationWrapper() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<ShoppingCreationWrapper> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<ShoppingCreationWrapper> shoppingList) {
        this.shoppingList = shoppingList;
    }

}
