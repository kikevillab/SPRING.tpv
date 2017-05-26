package services;

import static config.ResourceNames.BARCODES_PDFS_ROOT;
import static config.ResourceNames.BARCODE_PDF;
import static config.ResourceNames.INVOICES_PDFS_ROOT;
import static config.ResourceNames.INVOICE_PDF_FILENAME_ROOT;
import static config.ResourceNames.PDFS_ROOT;
import static config.ResourceNames.PDF_FILE_EXT;
import static config.ResourceNames.TICKETS_PDFS_ROOT;
import static config.ResourceNames.TICKET_PDF_FILENAME_ROOT;
import static config.ResourceNames.VOUCHERS_PDFS_ROOT;
import static config.ResourceNames.VOUCHER_PDF_FILENAME_ROOT;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import daos.core.EmbroideryDao;
import daos.core.InvoiceDao;
import daos.core.TextilePrintingDao;
import daos.core.TicketDao;
import daos.core.VoucherDao;
import entities.core.Embroidery;
import entities.core.Invoice;
import entities.core.Product;
import entities.core.TextilePrinting;
import entities.core.Ticket;
import entities.core.Voucher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class PdfGeneratorServiceIT {

    @Autowired
    private PdfGenerationService pdfGenService;

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private VoucherDao voucherDao;

    @Autowired
    private EmbroideryDao embroideryDao;

    @Autowired
    private TextilePrintingDao textilePrintingDao;

    @Test
    public void testGenerateInvoicePdf() throws IOException {
        Invoice invoice = invoiceDao.findAll().get(0);
        pdfGenService.generateInvoicePdf(invoice);
        String path = PDFS_ROOT + INVOICES_PDFS_ROOT + INVOICE_PDF_FILENAME_ROOT + invoice.getId() + PDF_FILE_EXT;
        File pdfFile = new File(path);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.canRead());
        assertTrue(pdfFile.canWrite());
    }

    @Test
    public void testGenerateTicketPdf() throws IOException {
        Ticket ticket = ticketDao.findAll().get(0);
        pdfGenService.generateTicketPdf(ticket);
        String path = PDFS_ROOT + TICKETS_PDFS_ROOT + TICKET_PDF_FILENAME_ROOT + ticket.getId() + PDF_FILE_EXT;
        File pdfFile = new File(path);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.canRead());
        assertTrue(pdfFile.canWrite());
    }

    @Test
    public void testGenerateVoucherPdf() throws IOException {
        Voucher voucher = voucherDao.findAll().get(0);
        pdfGenService.generateVoucherPdf(voucher);
        String path = PDFS_ROOT + VOUCHERS_PDFS_ROOT + VOUCHER_PDF_FILENAME_ROOT + voucher.getId() + PDF_FILE_EXT;
        File pdfFile = new File(path);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.canRead());
        assertTrue(pdfFile.canWrite());
    }
    
    @Test
    public void testGenerateBarcodesPdf() throws IOException {
        List<Product> embroideryAndTextile = new ArrayList<>();

        List<Embroidery> embroideryList = embroideryDao.findAll();
        embroideryAndTextile.addAll(embroideryList);
        
        List<TextilePrinting> textilePrintingList = textilePrintingDao.findAll();
        embroideryAndTextile.addAll(textilePrintingList);
        
        pdfGenService.generateBarcodesPdf(embroideryAndTextile);
        String path = PDFS_ROOT + BARCODES_PDFS_ROOT + BARCODE_PDF + PDF_FILE_EXT;
        File pdfFile = new File(path);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.canRead());
        assertTrue(pdfFile.canWrite());
    }

    @AfterClass
    public static void tearDownOnce() throws IOException {
        String path = PDFS_ROOT + INVOICES_PDFS_ROOT + INVOICE_PDF_FILENAME_ROOT + 20170001 + PDF_FILE_EXT;
        new File(path).delete();
        path = PDFS_ROOT + TICKETS_PDFS_ROOT + TICKET_PDF_FILENAME_ROOT + 1 + PDF_FILE_EXT;
        new File(path).delete();
        path = PDFS_ROOT + VOUCHERS_PDFS_ROOT + VOUCHER_PDF_FILENAME_ROOT + 1 + PDF_FILE_EXT;
        new File(path).delete();
        path = PDFS_ROOT + BARCODES_PDFS_ROOT + BARCODE_PDF + PDF_FILE_EXT;
        new File(path).delete();
    }

}
