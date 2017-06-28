package entities.core;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(InvoicePK.class)
public class Invoice {

    @Id
    private int year;
    
    @Id
    private int id;

    @Temporal(TemporalType.DATE)
    private Calendar created;

    @OneToOne
    @JoinColumns({@JoinColumn(name = "ticket_id", referencedColumnName = "id"),
            @JoinColumn(name = "ticket_date", referencedColumnName = "date")})
    private Ticket ticket;

    public Invoice() {
        created = Calendar.getInstance();
    }

    public Invoice(int id, Ticket ticket) {
        this();
        year = Calendar.getInstance().get(Calendar.YEAR);
        this.id = id;
        this.ticket = ticket;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + year;
        return result;
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
        return (id == ((Invoice) obj).id) && (year == ((Invoice) obj).year);
    }

    @Override
    public String toString() {
        String createdStr = new SimpleDateFormat("dd-MMM-yyyy").format(created.getTime());
        return "Invoice [year=" + year + ", id=" + id + ", created=" + createdStr + ", ticket=" + ticket + "]";
    }

}
