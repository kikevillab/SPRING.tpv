package controllers;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.InvoiceDao;
import daos.core.TicketDao;
import entities.core.Invoice;
import entities.core.Ticket;
import services.PdfGenerationService;

@Controller
public class PdfGenerationController {

    private PdfGenerationService pdfGenService;

    private InvoiceDao invoiceDao;

    private TicketDao ticketDao;

    @Autowired
    public void setPdfGenerationService(PdfGenerationService pdfGenService) {
        this.pdfGenService = pdfGenService;
    }

    @Autowired
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    @Autowired
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void generateInvoicePdf(int invoiceId) throws FileNotFoundException {
        Invoice invoice = invoiceDao.findOne(invoiceId);
        pdfGenService.generateInvoicePdf(invoice);
    }
    
    public void generateTicketPdf(long ticketId) throws FileNotFoundException {
        Ticket ticket = ticketDao.findOne(ticketId);
        pdfGenService.generateTicketPdf(ticket);
    }
    

}
