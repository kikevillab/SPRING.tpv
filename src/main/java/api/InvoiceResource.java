package api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.TicketUserInvalidException;
import api.exceptions.TicketInvoiceAlreadyAssignedException;
import config.ResourceNames;
import controllers.InvoiceController;
import controllers.TicketController;
import controllers.UserController;
import entities.core.Ticket;
import wrappers.InvoiceCreationResponseWrapper;
import wrappers.InvoiceWrapper;
import wrappers.TicketReferenceWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.INVOICES)
public class InvoiceResource {

    private InvoiceController invoiceController;

    private UserController userController;

    private TicketController ticketController;

    @Autowired
    public void setInvoiceController(InvoiceController invoiceController) {
        this.invoiceController = invoiceController;
    }

    @Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    @Autowired
    public void setTicketController(TicketController ticketController) {
        this.ticketController = ticketController;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<byte[]> createInvoice(@RequestBody TicketReferenceWrapper ticketReferenceWrapper)
            throws TicketUserInvalidException, TicketInvoiceAlreadyAssignedException, IOException {
        Ticket ticket = ticketController.findOneTicket(ticketReferenceWrapper);
        if (!userController.userIsValid(ticket.getUser())) {
            throw new TicketUserInvalidException("User: " + ticket.getUser());
        }
        if (ticketController.ticketIsAlreadyAssignedToInvoice(ticket)) {
            throw new TicketInvoiceAlreadyAssignedException("Ticket: " + ticket);
        }
        InvoiceCreationResponseWrapper invoiceWrapper = invoiceController.createInvoice(ticket);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "INVOICE" + invoiceWrapper.getInvoiceId() + ResourceNames.PDF_FILE_EXT;
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-" + "hcheck=0");
        ResponseEntity<byte[]> invoicePdf = new ResponseEntity<byte[]>(invoiceWrapper.getPdfByteArray(), headers, HttpStatus.OK);
        return invoicePdf;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<InvoiceWrapper> findAllInvoices() {
        return invoiceController.findAllInvoices();
    }

}
