package wrappers;

public class InvoiceWrapper {
    private int id;
    
    private TicketWrapper ticket;

    public InvoiceWrapper(){
    }
    
    public InvoiceWrapper(int id, TicketWrapper ticketWrapper) {
        super();
        this.id = id;
        this.ticket = ticketWrapper;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TicketWrapper getTicket() {
        return ticket;
    }

    public void setTicket(TicketWrapper ticket) {
        this.ticket = ticket;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        InvoiceWrapper other = (InvoiceWrapper) obj;
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
        return "InvoiceWrapper [id=" + id + ", ticket=" + ticket + "]";
    }
}
