package entities.core;

import java.io.Serializable;
import java.util.Calendar;

public class TicketPK implements Serializable {

    private long id;
    
    private Calendar created;
    
    private static final long serialVersionUID = 8700895349515977988L;

    public TicketPK() {
    }

    public TicketPK(long id) {
        super();
        this.id = id;
        this.created = Calendar.getInstance();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        result = prime * result + (int) (id ^ (id >>> 32));
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
        TicketPK other = (TicketPK) obj;
        if (created == null) {
            if (other.created != null)
                return false;
        } else if (!created.equals(other.created))
            return false;
        if (id != other.id)
            return false;
        return true;
    }

}
