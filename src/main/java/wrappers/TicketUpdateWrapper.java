package wrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketUpdateWrapper {

    private List<ShoppingUpdateWrapper> shoppingUpdateList;

    private List<String> vouchers;

    private float cash;

    public TicketUpdateWrapper() {
        super();
        shoppingUpdateList = new ArrayList<>();
    }

    public List<ShoppingUpdateWrapper> getShoppingUpdateList() {
        return shoppingUpdateList;
    }

    public void setShoppingUpdateList(List<ShoppingUpdateWrapper> shoppingUpdateList) {
        this.shoppingUpdateList = shoppingUpdateList;
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
        return "TicketUpdateWrapper [shoppingUpdateList=" + Arrays.toString(shoppingUpdateList.toArray()) + ", vouchers=" + vouchers + ", cash=" + cash + "]";
    }

}
