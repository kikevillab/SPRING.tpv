package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.Invoice;
import entities.core.InvoicePK;

public interface InvoiceDao extends JpaRepository<Invoice, InvoicePK> {

    Invoice findFirstByOrderByCreatedDescIdDesc();

    Invoice findByTicketReference(String reference);

}
