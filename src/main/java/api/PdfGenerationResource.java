package api;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import controllers.PdfGenerationController;
import wrappers.InvoiceIdWrapper;
import wrappers.TicketIdWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.PDF_GENERATION)
public class PdfGenerationResource {

    private PdfGenerationController pdfGenController;
    
    @Autowired
    public void setPdfGenerationController(PdfGenerationController pdfGenController){
        this.pdfGenController = pdfGenController;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void generateInvoicePdf(@RequestBody InvoiceIdWrapper invoiceIdWrapper) throws FileNotFoundException{
        int invoiceId = invoiceIdWrapper.getId();
        pdfGenController.generateInvoicePdf(invoiceId);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void generateTicketPdf(@RequestBody TicketIdWrapper ticketIdWrapper) throws FileNotFoundException{
        long ticketId = ticketIdWrapper.getId();
        pdfGenController.generateTicketPdf(ticketId);
    }
}
