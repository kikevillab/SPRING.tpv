package utils.pdfs;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import entities.core.Product;
import entities.core.Voucher;

@Service
@Transactional
public class PdfGenerationService {

    public byte[] generateVoucherPdf(Voucher voucher) throws IOException {
        return new VoucherPdfGenerator(voucher).generatePdf();
    }

    public byte[] generateBarcodesPdf(List<Product> productList) throws IOException {
        return new BarcodesPdfGenerator(productList).generatePdf();
    }
}
