package services;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.stereotype.Service;

import entities.core.Invoice;
import entities.core.Product;
import entities.core.Ticket;
import entities.core.Voucher;

@Service
public class PdfGenerationService {
    public void generateInvoicePdf(Invoice invoice) throws FileNotFoundException {
        new InvoicePdfGenerator(invoice).generatePdf();
    }

    public void generateTicketPdf(Ticket ticket) throws FileNotFoundException {
        new TicketPdfGenerator(ticket).generatePdf();
    }

    public void generateVoucherPdf(Voucher voucher) throws FileNotFoundException {
        new VoucherPdfGenerator(voucher).generatePdf();
    }
    
    public void generateBarcodesPdf(List<Product> embroideryAndTextile) throws FileNotFoundException {
        new BarcodesPdfGenerator(embroideryAndTextile).generatePdf();
    }
}
