package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.Invoice;
import entities.core.Ticket;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {
    Invoice findFirstByOrderByIdDesc();
    Invoice findByTicket(Ticket ticket);
}
