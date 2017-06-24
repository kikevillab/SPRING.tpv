package daos.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import entities.core.Invoice;
import entities.core.Ticket;
import entities.core.TicketPK;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})

public class InvoiceDaoIT {

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private TicketDao ticketDao;

    @Test
    public void testCount() {
        assertTrue(2 <= invoiceDao.count());
    }

    @Test
    public void testFindFirstByOrderByIdDesc() {
        assertEquals(20170002, invoiceDao.findFirstByOrderByCreatedDescIdDesc().getId());
    }

    @Test
    public void testFindByTicket() {
        Ticket ticket = ticketDao.findOne(new TicketPK(3L));
        Invoice invoice = invoiceDao.findByTicketReference(ticket.getReference());
        assertNotNull(invoice);
    }

}
