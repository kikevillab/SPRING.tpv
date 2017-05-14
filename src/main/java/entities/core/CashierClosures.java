package entities.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
public class CashierClosures {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private Calendar openingDate;

    @Column
    private Calendar closureDate;

    @Column
    private String comment;

    public CashierClosures() {
    }

    public CashierClosures(long id, int amount, Calendar openingDate) {
        this.id = id;
        this.amount = amount;
        this.openingDate = openingDate;
    }

    public CashierClosures(long id, int amount, Calendar openingDate, Calendar closureDate, String comment) {
        this.id = id;
        this.amount = amount;
        this.openingDate = openingDate;
        this.closureDate = closureDate;
        this.comment = comment;
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
}
