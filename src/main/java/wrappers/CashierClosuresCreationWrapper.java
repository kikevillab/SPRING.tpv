package wrappers;

import entities.core.CashierClosure;

import java.util.Calendar;

public class CashierClosuresCreationWrapper {

    private long id;

    private double amount;

    private Calendar openingDate;

    public CashierClosuresCreationWrapper() {
    }

    public CashierClosuresCreationWrapper(long id, double amount, Calendar openingDate) {
        this.id = id;
        this.amount = amount;
        this.openingDate = openingDate;
    }

    public CashierClosuresCreationWrapper(CashierClosure cashierClosure) {
        this.id = cashierClosure.getId();
        this.amount = cashierClosure.getAmount();
        this.openingDate = cashierClosure.getOpeningDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Calendar getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Calendar openingDate) {
        this.openingDate = openingDate;
    }
}
