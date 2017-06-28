package entities.core;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Invoice {

    @Id
    private int id;

    @Temporal(TemporalType.DATE)
    private Calendar created;

    @OneToOne
    private Ticket ticket;

    public Invoice() {
        created = Calendar.getInstance();
    }

    public Invoice(int id, Ticket ticket) {
        this();
        this.id = id;
        this.ticket = ticket;
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

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
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
        return (id == ((Invoice) obj).id);
    }

    @Override
    public String toString() {
        String createdStr = new SimpleDateFormat("dd-MMM-yyyy").format(created.getTime());
        return "Invoice [id=" + id + ", created=" + createdStr + ", ticket=" + ticket + "]";
    }

}
