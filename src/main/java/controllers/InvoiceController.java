package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.InvoiceDao;
import entities.core.Invoice;
import entities.core.Ticket;

@Controller
public class InvoiceController {

    private InvoiceDao invoiceDao;
        
    @Autowired
    public void setInvoiceDao(InvoiceDao invoiceDao){
        this.invoiceDao = invoiceDao;
    }
    
    public List<Invoice> findAllInvoices(){
        return invoiceDao.findAll();
    }
    
    public Invoice createInvoice(Ticket ticket){
        Invoice invoice = new Invoice(getNextInvoiceId(), ticket);
        return invoiceDao.save(invoice);      
    }
    
    private int getNextInvoiceId(){
        boolean thereAreNoInvoices = invoiceDao.count() == 0;
        int nextInvoiceId;
        if(thereAreNoInvoices){
            nextInvoiceId = 20170001;
        } else {
            nextInvoiceId = invoiceDao.findFirstByOrderByIdDesc().getId();
        }
        return nextInvoiceId;
    }
}
