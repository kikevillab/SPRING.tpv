package wrappers;

import entities.core.Shopping;
import entities.core.Ticket;

public class DayTicketWrapper {

    private String reference;

    private double total;

    public DayTicketWrapper(Ticket ticket) {
        this.reference = ticket.getReference();
        this.total = 0;
        if (ticket.getShoppingList() != null) {
            for (Shopping shopping : ticket.getShoppingList()) {
                this.total += shopping.getShoppingTotal();
            }
        }
    }

    public String getReference() {
        return reference;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "DayTicketWrapper [reference=" + reference + ", total=" + total + "]";
    }

}
