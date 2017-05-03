package services;

import static config.ResourceNames.INVOICES_PDFS_ROOT;
import static config.ResourceNames.INVOICE_PDF_FILENAME_ROOT;
import static config.ResourceNames.PDFS_ROOT;
import static config.ResourceNames.PDF_FILE_EXT;
import static config.ResourceNames.TICKETS_PDFS_ROOT;
import static config.ResourceNames.TICKET_PDF_FILENAME_ROOT;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import daos.core.InvoiceDao;
import daos.core.TicketDao;
import entities.core.Invoice;
import entities.core.Ticket;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class PdfGeneratorServiceIT {

    @Autowired
    private PdfGenerationService pdfGenService;

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private TicketDao ticketDao;

    @Test
    public void testGenerateInvoicePdf() throws FileNotFoundException {
        Invoice invoice = invoiceDao.findAll().get(0);
        pdfGenService.generateInvoicePdf(invoice);
        String path = PDFS_ROOT + INVOICES_PDFS_ROOT + INVOICE_PDF_FILENAME_ROOT + invoice.getId() + PDF_FILE_EXT;
        File pdfFile = new File(path);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.canRead());
        assertTrue(pdfFile.canWrite());
    }

    @Test
    public void testGenerateTicketPdf() throws FileNotFoundException {
        Ticket ticket = ticketDao.findAll().get(0);
        pdfGenService.generateTicketPdf(ticket);
        String path = PDFS_ROOT + TICKETS_PDFS_ROOT + TICKET_PDF_FILENAME_ROOT + ticket.getId() + PDF_FILE_EXT;
        File pdfFile = new File(path);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.canRead());
        assertTrue(pdfFile.canWrite());
    }

    @After
    public void tearDown() throws IOException {
        String path = PDFS_ROOT + INVOICES_PDFS_ROOT + INVOICE_PDF_FILENAME_ROOT + 20170001 + PDF_FILE_EXT;
        new File(path).delete();
        path = PDFS_ROOT + TICKETS_PDFS_ROOT + TICKET_PDF_FILENAME_ROOT + 1 + PDF_FILE_EXT;
        new File(path).delete();
    }

}
