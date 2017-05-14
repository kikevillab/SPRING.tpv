package wrappers;


import entities.core.CashierClosures;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Calendar;

public class CashierClosuresWrapper {


    private long id;

    private int amount;

    private Calendar openingDate;

    private Calendar closureDate;

    private String comment;

    public CashierClosuresWrapper(CashierClosures cashierClosures) {
        this.id = cashierClosures.getId();
        this.amount = cashierClosures.getAmount();
        this.openingDate = cashierClosures.getOpeningDate();
        this.closureDate = cashierClosures.getClosureDate();
        this.comment = cashierClosures.getComment();
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
