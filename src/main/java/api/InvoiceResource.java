package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.InvoiceDoesNotAllowNotClosedTicketsException;
import api.exceptions.TicketHasInvalidUserException;
import api.exceptions.TicketIsAlreadyAssignedToInvoiceException;
import controllers.InvoiceController;
import controllers.TicketController;
import controllers.UserController;
import entities.core.Invoice;
import entities.core.Ticket;
import wrappers.TicketIdWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.INVOICES)
public class InvoiceResource {
    
    private InvoiceController invoiceController;
    
    private UserController userController;
    
    private TicketController ticketController;
    
    @Autowired
    public void setInvoiceController(InvoiceController invoiceController){
        this.invoiceController = invoiceController;
    }
    
    @Autowired
    public void setUserController(UserController userController){
        this.userController = userController;
    }
    
    @Autowired
    public void setTicketController(TicketController ticketController){
        this.ticketController = ticketController;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void createInvoice(@RequestBody TicketIdWrapper ticketIdWrapper) throws TicketHasInvalidUserException, InvoiceDoesNotAllowNotClosedTicketsException, TicketIsAlreadyAssignedToInvoiceException{
        Ticket ticket = ticketController.findOneTicket(ticketIdWrapper);
        if(!userController.userIsValid(ticket.getUser())){
            throw new TicketHasInvalidUserException("User: " + ticket.getUser());
        }
        if(ticketController.ticketIsAssignedToInvoice(ticket)){
            throw new TicketIsAlreadyAssignedToInvoiceException();
        }
        if(!ticketController.ticketIsClosed(ticket)){
            throw new InvoiceDoesNotAllowNotClosedTicketsException();
        }
        invoiceController.createInvoice(ticket);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Invoice> findAllInvoices(){
        return invoiceController.findAllInvoices();
    }

}
