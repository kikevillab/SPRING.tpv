package controllers;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.InvoiceDao;
import daos.core.TicketDao;
import daos.core.VoucherDao;
import entities.core.Invoice;
import entities.core.InvoicePK;
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

    public void generateInvoicePdf(int invoiceId) throws FileNotFoundException {
        Invoice invoice = invoiceDao.findOne(new InvoicePK(invoiceId));
        pdfGenService.generateInvoicePdf(invoice);
    }
    
    public void generateTicketPdf(long ticketId) throws FileNotFoundException {
        Ticket ticket = ticketDao.findOne(new TicketPK(ticketId));
        pdfGenService.generateTicketPdf(ticket);
    }
    
    public void generateVoucherPdf(int voucherId) throws FileNotFoundException {
        Voucher voucher = voucherDao.findOne(voucherId);
        pdfGenService.generateVoucherPdf(voucher);
    }
    
    public boolean ticketExists(long ticketId){
        return ticketDao.findOne(new TicketPK(ticketId)) != null;
    }
    
    public boolean invoiceExists(int invoiceId){
        return invoiceDao.findOne(new InvoicePK(invoiceId)) != null;
    }
    
    public boolean voucherExists(int voucherId){
        return voucherDao.exists(voucherId);
    }

}
