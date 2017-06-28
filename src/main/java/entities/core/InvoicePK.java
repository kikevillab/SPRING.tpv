package entities.core;

import java.io.Serializable;
import java.util.Calendar;

public class InvoicePK implements Serializable {
    private static final long serialVersionUID = 2959464363855159325L;

    private int year;
    
    private int id;

    public InvoicePK() {
    }
    
    public InvoicePK(int id) {
        this(Calendar.getInstance().get(Calendar.YEAR),id);
    }
    
    public InvoicePK(int year, int id) {
        this.year = year;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
        return (id == ((InvoicePK) obj).id) && (year == ((InvoicePK) obj).year);
    }

    @Override
    public String toString() {
        return "InvoicePK [year=" + year + ", id=" + id + "]";
    }
    
}
