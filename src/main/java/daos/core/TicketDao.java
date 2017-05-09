package daos.core;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.Ticket;
import entities.core.TicketPK;

public interface TicketDao extends JpaRepository<Ticket, TicketPK> {

    public Ticket findFirstByOrderByCreatedDescIdDesc();
    
    public Ticket findFirstByReference(String reference);
    
    public List<Ticket> findByCreated(Calendar created);
}
