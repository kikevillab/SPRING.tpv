package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

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
import entities.core.TicketPK;
import wrappers.InvoiceCreationResponseWrapper;

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
    public void testFindAllInvoices() {
        assertNotNull(invoiceController.findAllInvoices());
    }

    @Test
    public void testCreateInvoiceWithAtLeastOneInvoiceThisYear() throws IOException {
        Ticket ticket = ticketDao.findOne(new TicketPK(2L));
        Invoice latestInvoice = invoiceDao.findFirstByOrderByCreatedDescIdDesc();
        InvoiceCreationResponseWrapper responseWrapper = invoiceController.createInvoice(ticket);
        assertNotNull(responseWrapper);
        assertEquals(latestInvoice.getId() + 1, responseWrapper.getInvoiceId());
        invoiceDao.delete(new InvoicePK(responseWrapper.getInvoiceId()));
    }

    @Test
    public void testCreateInvoiceWithNoInvoicesThisYear() throws IOException {
        List<Invoice> invoiceList = invoiceDao.findAll();
        invoiceDao.deleteAll();
        Ticket ticket = ticketDao.findOne(new TicketPK(2L));
        InvoiceCreationResponseWrapper responseWrapper = invoiceController.createInvoice(ticket);
        assertNotNull(responseWrapper);
        int resultInvoiceId = Integer.parseInt(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + 1);
        assertEquals(resultInvoiceId, responseWrapper.getInvoiceId());
        invoiceDao.delete(new InvoicePK(responseWrapper.getInvoiceId()));
        invoiceDao.save(invoiceList);
    }
}
