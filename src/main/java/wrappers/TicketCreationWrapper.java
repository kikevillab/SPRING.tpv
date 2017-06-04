package wrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketCreationWrapper {

    private Long userMobile;

    private List<ShoppingCreationWrapper> shoppingList;

    private List<String> vouchers;

    private float cash;

    public TicketCreationWrapper() {
        super();
        shoppingList = new ArrayList<>();
        vouchers = new ArrayList<>();
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

    public List<String> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<String> vouchers) {
        this.vouchers = vouchers;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    @Override
    public String toString() {
        return "TicketCreationWrapper [userMobile=" + userMobile + ", shoppingList=" + Arrays.toString(shoppingList.toArray())
                + ", vouchers=" + vouchers + ", cash=" + cash + "]";
    }

}
