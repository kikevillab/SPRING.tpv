package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.Ticket;

public interface TicketDao extends JpaRepository<Ticket, Long> {

    public Ticket findFirstByOrderByCreatedDescIdDesc();
}
