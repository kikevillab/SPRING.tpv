package wrappers;

import java.util.List;

public class TicketCreationWrapper {

    private int userId;

    private List<ShoppingCreationWrapper> shoppingList;

    public TicketCreationWrapper() {
        super();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<ShoppingCreationWrapper> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<ShoppingCreationWrapper> shoppingList) {
        this.shoppingList = shoppingList;
    }

}
