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

//TODO Cambiar clave a int-int, cuando se imprime se realiza mediante int/int
@Entity
@IdClass(InvoicePK.class)
public class Invoice {

    @Id
    private int id;
    
    @Id
    @Temporal(TemporalType.DATE)
    private Calendar created;

    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "ticket_id", referencedColumnName = "id"),
        @JoinColumn(name = "ticket_created", referencedColumnName = "created")
      })
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
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + id;
        result = prime * result + ((ticket == null) ? 0 : ticket.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Invoice other = (Invoice) obj;
        if (created == null) {
            if (other.created != null)
                return false;
        } else if (!created.equals(other.created))
            return false;
        if (id != other.id)
            return false;
        if (ticket == null) {
            if (other.ticket != null)
                return false;
        } else if (!ticket.equals(other.ticket))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String createdStr = new SimpleDateFormat("dd-MMM-yyyy").format(created.getTime());
        return "Invoice [id=" + id + ", created=" + createdStr + ", ticket=" + ticket + "]";
    }



}
