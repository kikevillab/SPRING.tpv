package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.InvoiceDao;
import entities.core.Invoice;
import entities.core.Ticket;
import utils.pdfs.PdfGenerator;
import wrappers.InvoiceCreationResponseWrapper;
import wrappers.InvoiceIdWrapper;
import wrappers.InvoiceWrapper;
import wrappers.TicketIdWrapper;

@Controller
public class InvoiceController {

    private InvoiceDao invoiceDao;

    private PdfGenerator pdfGenerator;

    @Autowired
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    @Autowired
    public void setPdfGenerator(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }

    public List<InvoiceWrapper> findAllInvoices() {
        List<InvoiceWrapper> invoiceWrappers = new ArrayList<>();
        for (Invoice invoice : invoiceDao.findAll()) {
            invoiceWrappers.add(new InvoiceWrapper(invoice.getId(), new TicketIdWrapper(invoice.getTicket().getId())));
        }
        return invoiceWrappers;
    }

    public Invoice findOneInvoice(InvoiceIdWrapper invoiceIdWrapper) {
        return invoiceDao.findOne(invoiceIdWrapper.getId());
    }

    public InvoiceCreationResponseWrapper createInvoice(Ticket ticket) throws IOException {
        Invoice invoice = new Invoice(this.getNextInvoiceId(), ticket);
        Invoice invoiceCreated = invoiceDao.save(invoice);
        byte[] pdfByteArray = pdfGenerator.generate(invoiceCreated);
        return new InvoiceCreationResponseWrapper(invoiceCreated.getId(), pdfByteArray);
    }

    private int getNextInvoiceId() {
        Invoice latestInvoice = invoiceDao.findFirstByOrderByCreatedDescIdDesc();
        boolean thereAreInvoices = latestInvoice != null;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String currentYearString = String.valueOf(currentYear);
        int nextInvoiceId = Integer.parseInt(currentYearString + 1);
        if (thereAreInvoices) {
            int latestInvoiceYear = latestInvoice.getCreated().get(Calendar.YEAR);
            boolean latestInvoiceWasCreatedThisYear = latestInvoiceYear == currentYear;
            if (latestInvoiceWasCreatedThisYear) {
                nextInvoiceId = latestInvoice.getId() + 1;
            }
        }
        return nextInvoiceId;
    }

}
