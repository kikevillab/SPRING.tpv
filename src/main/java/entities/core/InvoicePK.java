package entities.core;

import java.io.Serializable;
import java.util.Calendar;

public class InvoicePK implements Serializable {
    private static final long serialVersionUID = 2959464363855159325L;

    private int id;

    private Calendar created;

    public InvoicePK() {
        
    }
    
    public InvoicePK(int id) {
        super();
        this.id = id;
        this.created = Calendar.getInstance();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        result = prime * result + id;
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
        InvoicePK other = (InvoicePK) obj;
        if (created == null) {
            if (other.created != null)
                return false;
        } else if (!created.equals(other.created))
            return false;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "InvoicePK [id=" + id + ", created=" + created + "]";
    }
    
}
