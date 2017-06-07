package wrappers;


import entities.core.CashierClosure;

import java.util.Calendar;

public class CashierClosuresWrapper {


    private long id;

    private double amount;

    private Calendar openingDate;

    private Calendar closureDate;

    private String comment;

    public CashierClosuresWrapper(CashierClosure cashierClosure) {
        this.id = cashierClosure.getId();
        this.amount = cashierClosure.getAmount();
        this.openingDate = cashierClosure.getOpeningDate();
        this.closureDate = cashierClosure.getClosureDate();
        this.comment = cashierClosure.getComment();
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

    public Calendar getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(Calendar closureDate) {
        this.closureDate = closureDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CashierClosuresWrapper{" +
                "id=" + id +
                ", amount=" + amount +
                ", openingDate=" + openingDate +
                ", closureDate=" + closureDate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
