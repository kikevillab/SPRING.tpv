package wrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import entities.core.Shopping;
import entities.core.Ticket;

public class TicketWrapper {
    
    private long id;

    private Calendar created;

    private String reference;

    private List<ShoppingWrapper> shoppingList;

    private Long userMobile;

    public TicketWrapper() {
        
    }

    public TicketWrapper(Ticket ticket) {
        this.id = ticket.getId();
        this.created = ticket.getCreated();
        this.reference = ticket.getReference();
        this.shoppingList = new ArrayList<>();
        if (ticket.getShoppingList() != null) {
            for (Shopping shopping : ticket.getShoppingList()) {
                this.shoppingList.add(new ShoppingWrapper(shopping));
            }
        }
        if (ticket.getUser() != null) {
            this.userMobile = ticket.getUser().getMobile();
        }
    }

    public long getId() {
        return id;
    }

    public Calendar getCreated() {
        return created;
    }

    public String getReference() {
        return reference;
    }

    public List<ShoppingWrapper> getShoppingList() {
        return shoppingList;
    }

    public Long getUserMobile() {
        return userMobile;
    }

    @Override
    public String toString() {
        String string = "TicketWrapper [id=" + id + ", created=" + created + ", reference=" + reference + ", shoppingList=" + Arrays.toString(shoppingList.toArray());
        if (userMobile != null) {
            string += ", userMobile=" + userMobile;
        }
        string += "]";
        return string;
                
    }
}
