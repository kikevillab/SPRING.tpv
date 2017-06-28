package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.Invoice;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {

    Invoice findFirstByOrderByCreatedDescIdDesc();

    Invoice findByTicketReference(String reference);

}
