package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.Invoice;
import entities.core.InvoicePK;
import entities.core.Ticket;

public interface InvoiceDao extends JpaRepository<Invoice, InvoicePK> {
    Invoice findFirstByOrderByCreatedDescIdDesc();
    Invoice findByTicket(Ticket ticket);
}
