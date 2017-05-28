package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.EmbroideryDao;
import daos.core.InvoiceDao;
import daos.core.TextilePrintingDao;
import daos.core.TicketDao;
import daos.core.VoucherDao;
import entities.core.Embroidery;
import entities.core.Invoice;
import entities.core.InvoicePK;
import entities.core.Product;
import entities.core.TextilePrinting;
import entities.core.Ticket;
import entities.core.TicketPK;
import entities.core.Voucher;
import services.PdfGenerationService;

@Controller
public class PdfGenerationController {

    private PdfGenerationService pdfGenService;

    private InvoiceDao invoiceDao;

    private TicketDao ticketDao;

    private VoucherDao voucherDao;

    private EmbroideryDao embroideryDao;

    private TextilePrintingDao textilePrintingDao;

    @Autowired
    public void setPdfGenerationService(PdfGenerationService pdfGenService) {
        this.pdfGenService = pdfGenService;
    }

    @Autowired
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    @Autowired
    public void setVoucherDao(VoucherDao voucherDao) {
        this.voucherDao = voucherDao;
    }

    @Autowired
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Autowired
    public void setEmbroideryDao(EmbroideryDao embroideryDao) {
        this.embroideryDao = embroideryDao;
    }

    @Autowired
    public void setTextilePrintingDao(TextilePrintingDao textilePrintingDao) {
        this.textilePrintingDao = textilePrintingDao;
    }

    public void generateInvoicePdf(int invoiceId) throws IOException {
        Invoice invoice = invoiceDao.findOne(new InvoicePK(invoiceId));
        pdfGenService.generateInvoicePdf(invoice);
    }

    public void generateTicketPdf(long ticketId) throws IOException {
        Ticket ticket = ticketDao.findOne(new TicketPK(ticketId));
        pdfGenService.generateTicketPdf(ticket);
    }

    public void generateVoucherPdf(int voucherId) throws IOException {
        Voucher voucher = voucherDao.findOne(voucherId);
        pdfGenService.generateVoucherPdf(voucher);
    }

    public void generateBarcodesPdf() throws IOException {
        List<Product> embroideryAndTextile = new ArrayList<>();

        List<Embroidery> embroideryList = embroideryDao.findAll();
        embroideryAndTextile.addAll(embroideryList);
        
        List<TextilePrinting> textilePrintingList = textilePrintingDao.findAll();
        embroideryAndTextile.addAll(textilePrintingList);

        pdfGenService.generateBarcodesPdf(embroideryAndTextile);
    }

    public boolean ticketExists(long ticketId) {
        return ticketDao.findOne(new TicketPK(ticketId)) != null;
    }

    public boolean invoiceExists(int invoiceId) {
        return invoiceDao.findOne(new InvoicePK(invoiceId)) != null;
    }

    public boolean voucherExists(int voucherId) {
        return voucherDao.exists(voucherId);
    }

}
