package controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.InvoiceDao;
import daos.core.ProductDao;
import daos.core.TicketDao;
import daos.core.VoucherDao;
import entities.core.Invoice;
import entities.core.InvoicePK;
import entities.core.Product;
import entities.core.Ticket;
import entities.core.TicketPK;
import entities.core.Voucher;
import services.PdfGenerationService;

@Controller
public class PdfGenerationController {

    private PdfGenerationService pdfGenService;

    private InvoiceDao invoiceDao;

    private TicketDao ticketDao;

    private VoucherDao voucherDao;

    private ProductDao productDao;

    @Autowired
    public void setPdfGenerationService(PdfGenerationService pdfGenService) {
        this.pdfGenService = pdfGenService;
    }

    @Autowired
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    @Autowired
    public void setVoucherDao(VoucherDao voucherDao) {
        this.voucherDao = voucherDao;
    }

    @Autowired
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void generateInvoicePdf(int invoiceId) throws IOException {
        Invoice invoice = invoiceDao.findOne(new InvoicePK(invoiceId));
        pdfGenService.generateInvoicePdf(invoice);
    }

    public void generateTicketPdf(long ticketId) throws IOException {
        Ticket ticket = ticketDao.findOne(new TicketPK(ticketId));
        pdfGenService.generateTicketPdf(ticket);
    }

    public void generateVoucherPdf(int voucherId) throws IOException {
        Voucher voucher = voucherDao.findOne(voucherId);
        pdfGenService.generateVoucherPdf(voucher);
    }

    public void generateBarcodesPdf(List<String> productCodeList) throws IOException {
        List<Product> productList = productDao.findAll(productCodeList);
        pdfGenService.generateBarcodesPdf(productList);
    }

    public boolean ticketExists(long ticketId) {
        return ticketDao.findOne(new TicketPK(ticketId)) != null;
    }

    public boolean invoiceExists(int invoiceId) {
        return invoiceDao.findOne(new InvoicePK(invoiceId)) != null;
    }

    public boolean voucherExists(int voucherId) {
        return voucherDao.exists(voucherId);
    }

    public boolean productExists(String code) {
        return productDao.exists(code);
    }

}
