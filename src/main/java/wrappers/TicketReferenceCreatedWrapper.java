package wrappers;

import entities.core.Ticket;

public class TicketReferenceCreatedWrapper {
    
    private String reference;

    private long created;

    public TicketReferenceCreatedWrapper() {
        super();
    }

    public TicketReferenceCreatedWrapper(Ticket ticket) {
        super();
        this.reference = ticket.getReference();
        this.created = ticket.getCreated().getTimeInMillis();
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
