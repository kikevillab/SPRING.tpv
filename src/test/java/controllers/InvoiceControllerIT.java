package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsControllerConfig;
import config.TestsPersistenceConfig;
import daos.core.InvoiceDao;
import daos.core.TicketDao;
import entities.core.Invoice;
import entities.core.InvoicePK;
import entities.core.Ticket;
import wrappers.InvoiceWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class InvoiceControllerIT {

    @Autowired
    private InvoiceController invoiceController;
    
    @Autowired 
    private TicketDao ticketDao;
    
    @Autowired
    private InvoiceDao invoiceDao;
    
    @Test
    public void testFindAllInvoices(){
        assertNotNull(invoiceController.findAllInvoices());
    }
    
    @Test
    public void testCreateInvoice() {
        Ticket ticket = ticketDao.findOne(2L);
        Invoice latestInvoice = invoiceDao.findFirstByOrderByIdDesc();
        InvoiceWrapper invoice = invoiceController.createInvoice(ticket);
        assertNotNull(invoice);
        assertEquals(latestInvoice.getId() + 1, invoice.getId());      
        invoiceDao.delete(new InvoicePK(invoice.getId()));
    }
}
