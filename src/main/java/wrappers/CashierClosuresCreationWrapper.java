package wrappers;

import entities.core.CashierClosures;

import java.util.Calendar;

public class CashierClosuresCreationWrapper {

    private long id;

    private int amount;

    private Calendar openingDate;

    public CashierClosuresCreationWrapper() {
    }

    public CashierClosuresCreationWrapper(long id, int amount, Calendar openingDate) {
        this.id = id;
        this.amount = amount;
        this.openingDate = openingDate;
    }

    public CashierClosuresCreationWrapper(CashierClosures cashierClosures) {
        this.id = cashierClosures.getId();
        this.amount = cashierClosures.getAmount();
        this.openingDate = cashierClosures.getOpeningDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Calendar getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Calendar openingDate) {
        this.openingDate = openingDate;
    }
}
