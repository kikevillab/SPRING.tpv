package entities.core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TicketPK implements Serializable {
    private static final long serialVersionUID = 8700895349515977988L;

    private int date;

    private int id;

    public TicketPK() {
    }

    public TicketPK(int id) {
        this(Integer.parseInt((new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()))), id);
    }

    public TicketPK(int date, int id) {
        this.date = date;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + date;
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
        return (id == ((TicketPK) obj).id) && (date == ((TicketPK) obj).date);
    }

    @Override
    public String toString() {
        return "TicketPK [date=" + date + ", id=" + id + "]";
    }

}
