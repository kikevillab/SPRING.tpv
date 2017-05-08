package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.InvoiceDao;
import entities.core.Invoice;
import entities.core.InvoicePK;
import entities.core.Ticket;
import wrappers.InvoiceIdWrapper;
import wrappers.InvoiceWrapper;
import wrappers.TicketWrapper;

@Controller
public class InvoiceController {

    private InvoiceDao invoiceDao;
        
    @Autowired
    public void setInvoiceDao(InvoiceDao invoiceDao){
        this.invoiceDao = invoiceDao;
    }
    
    public List<InvoiceWrapper> findAllInvoices(){
        List<InvoiceWrapper> invoiceWrappers = new ArrayList<>();
        for(Invoice invoice : invoiceDao.findAll()){
            invoiceWrappers.add(new InvoiceWrapper(invoice.getId(), new TicketWrapper(invoice.getTicket().getId())));
        }
        return invoiceWrappers;
    }
    
    public Invoice findOneInvoice(InvoiceIdWrapper invoiceIdWrapper){
        return invoiceDao.findOne(new InvoicePK(invoiceIdWrapper.getId()));
    }
    
    public InvoiceWrapper createInvoice(Ticket ticket){
        Invoice invoice = new Invoice(getNextInvoiceId(), ticket);
        Invoice invoiceCreated = invoiceDao.save(invoice);
        return new InvoiceWrapper(invoiceCreated.getId(), new TicketWrapper(invoiceCreated.getTicket().getId()));      
    }
    
    private int getNextInvoiceId(){
        boolean thereAreNoInvoices = invoiceDao.count() == 0;
        int nextInvoiceId;
        if(thereAreNoInvoices){
            nextInvoiceId = 20170001;
        } else {
            nextInvoiceId = invoiceDao.findFirstByOrderByIdDesc().getId() + 1;
        }
        return nextInvoiceId;
    }
    
    
}
