package wrappers;

import java.math.BigDecimal;
import java.util.Calendar;

public class VoucherCreationWrapper {

    private int id;

    private String reference;

    private BigDecimal value;

    private Calendar created;

    private Calendar dateOfUse;
    
    public VoucherCreationWrapper(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getDateOfUse() {
        return dateOfUse;
    }

    public void setDateOfUse(Calendar dateOfUse) {
        this.dateOfUse = dateOfUse;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + ((dateOfUse == null) ? 0 : dateOfUse.hashCode());
        result = prime * result + id;
        result = prime * result + ((reference == null) ? 0 : reference.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        VoucherCreationWrapper other = (VoucherCreationWrapper) obj;
        if (created == null) {
            if (other.created != null)
                return false;
        } else if (!created.equals(other.created))
            return false;
        if (dateOfUse == null) {
            if (other.dateOfUse != null)
                return false;
        } else if (!dateOfUse.equals(other.dateOfUse))
            return false;
        if (id != other.id)
            return false;
        if (reference == null) {
            if (other.reference != null)
                return false;
        } else if (!reference.equals(other.reference))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "VoucherCreationWrapper [id=" + id + ", reference=" + reference + ", value=" + value + ", created=" + created
                + ", dateOfUse=" + dateOfUse + "]";
    }

    
}
