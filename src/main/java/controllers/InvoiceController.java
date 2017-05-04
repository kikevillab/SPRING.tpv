package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.InvoiceDao;
import entities.core.Invoice;

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

}
