package services;

import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entities.core.Invoice;

@Service
@Transactional
public class PdfGeneratorService {

	public void generateInvoicePdf(Invoice invoice) throws FileNotFoundException {
		new InvoicePdfGenerator().generatePdf(invoice);
	}
}
