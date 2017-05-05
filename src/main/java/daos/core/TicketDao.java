package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.Ticket;
import entities.core.TicketPK;

public interface TicketDao extends JpaRepository<Ticket, TicketPK> {

    public Ticket findFirstByOrderByCreatedDescIdDesc();
    
    public Ticket findFirstByReference(String reference);
}
