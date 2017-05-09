package controllers;

import java.util.ArrayList;
import java.util.Calendar;
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
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public List<InvoiceWrapper> findAllInvoices() {
        List<InvoiceWrapper> invoiceWrappers = new ArrayList<>();
        for (Invoice invoice : invoiceDao.findAll()) {
            invoiceWrappers.add(new InvoiceWrapper(invoice.getId(), new TicketWrapper(invoice.getTicket().getId())));
        }
        return invoiceWrappers;
    }

    public Invoice findOneInvoice(InvoiceIdWrapper invoiceIdWrapper) {
        return invoiceDao.findOne(new InvoicePK(invoiceIdWrapper.getId()));
    }

    public InvoiceWrapper createInvoice(Ticket ticket) {
        Invoice invoice = new Invoice(getNextInvoiceId(), ticket);
        Invoice invoiceCreated = invoiceDao.save(invoice);
        return new InvoiceWrapper(invoiceCreated.getId(), new TicketWrapper(invoiceCreated.getTicket().getId()));
    }

    private int getNextInvoiceId() {
        Invoice latestInvoice = invoiceDao.findFirstByOrderByCreatedDescIdDesc();
        boolean thereAreInvoices = latestInvoice != null;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int nextInvoiceId = currentYear + 1;
        if (thereAreInvoices) {
            int latestInvoiceYear = latestInvoice.getCreated().get(Calendar.YEAR);
            boolean latestInvoiceWasCreatedThisYear = latestInvoiceYear == currentYear;
            if (latestInvoiceWasCreatedThisYear) {
                nextInvoiceId = currentYear + latestInvoice.getId() + 1;
            }
        }
        return nextInvoiceId;
    }

}
