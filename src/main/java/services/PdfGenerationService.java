package services;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import entities.core.Invoice;
import entities.core.Product;
import entities.core.Ticket;
import entities.core.Voucher;

@Service
@Transactional
public class PdfGenerationService {

    public byte[] generateInvoicePdf(Invoice invoice) throws IOException {
        return new InvoicePdfGenerator(invoice).generatePdf();
    }

    public byte[] generateTicketPdf(Ticket ticket) throws IOException {
        return new TicketPdfGenerator(ticket).generatePdf();
    }

    public byte[] generateVoucherPdf(Voucher voucher) throws IOException {
        return new VoucherPdfGenerator(voucher).generatePdf();
    }

    public byte[] generateBarcodesPdf(List<Product> productList) throws IOException {
        return new BarcodesPdfGenerator(productList).generatePdf();
    }
}
