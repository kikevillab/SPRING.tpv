package api;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.InvoiceNotFoundException;
import api.exceptions.TicketNotFoundException;
import api.exceptions.VoucherNotFoundException;
import controllers.PdfGenerationController;
import wrappers.InvoiceIdWrapper;
import wrappers.TicketIdWrapper;
import wrappers.VoucherIdWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.PDF_GENERATION)
public class PdfGenerationResource {

    private PdfGenerationController pdfGenController;
    
    @Autowired
    public void setPdfGenerationController(PdfGenerationController pdfGenController){
        this.pdfGenController = pdfGenController;
    }
    
    @RequestMapping(value = Uris.INVOICES, method = RequestMethod.POST)
    public void generateInvoicePdf(@RequestBody InvoiceIdWrapper invoiceIdWrapper) throws FileNotFoundException, InvoiceNotFoundException{
        int invoiceId = invoiceIdWrapper.getId();
        if(pdfGenController.invoiceExists(invoiceId)){
            pdfGenController.generateInvoicePdf(invoiceId);
        } else {
            throw new InvoiceNotFoundException("Invoice: " + invoiceId);
        }   
    }
    
    @RequestMapping(value = Uris.TICKETS, method = RequestMethod.POST)
    public void generateTicketPdf(@RequestBody TicketIdWrapper ticketIdWrapper) throws FileNotFoundException, TicketNotFoundException{
        long ticketId = ticketIdWrapper.getId();
        if(pdfGenController.ticketExists(ticketId)){
            pdfGenController.generateTicketPdf(ticketId);
        } else {
            throw new TicketNotFoundException("Ticket: " + ticketId);
        }
    }
    
    @RequestMapping(value = Uris.VOUCHERS, method = RequestMethod.POST)
    public void generateVoucherPdf(@RequestBody VoucherIdWrapper voucherIdWrapper) throws FileNotFoundException, VoucherNotFoundException{
        int voucherId = voucherIdWrapper.getId();
        if(pdfGenController.voucherExists(voucherId)){
            pdfGenController.generateVoucherPdf(voucherId);
        } else {
            throw new VoucherNotFoundException("Voucher: " + voucherId);
        }
    }
}
