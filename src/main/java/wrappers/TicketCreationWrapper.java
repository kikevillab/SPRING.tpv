package wrappers;

import java.util.ArrayList;
import java.util.List;

public class TicketCreationWrapper {

    private Long userMobile;

    private List<ShoppingCreationWrapper> shoppingList;

    public TicketCreationWrapper() {
        super();
        shoppingList = new ArrayList<>();
    }

    public Long getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(Long userMobile) {
        this.userMobile = userMobile;
    }

    public List<ShoppingCreationWrapper> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<ShoppingCreationWrapper> shoppingList) {
        this.shoppingList = shoppingList;
    }

}
