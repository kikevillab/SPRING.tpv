package entities.core;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(InvoicePK.class)
public class Invoice {

    @Id
    private int id;
    
    @Id
    @Temporal(TemporalType.DATE)
    private Calendar created;

    @OneToOne
    @JoinColumn
    private Ticket ticket;

    public Invoice() {
        created = Calendar.getInstance();
    }

    public Invoice(int id, Ticket ticket) {
        this.id = id;
        this.ticket = ticket;
        created = Calendar.getInstance();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id == ((Invoice) obj).id;
    }

    @Override
    public String toString() {
        return "Invoice[" + id + ": ticket=" + ticket + "]";
    }

}
