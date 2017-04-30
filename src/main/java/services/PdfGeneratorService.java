package services;

import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;

import entities.core.Invoice;

@Service
public class PdfGeneratorService {
	public void generateInvoicePdf(Invoice invoice) throws FileNotFoundException {
		new InvoicePdfGenerator(invoice).generatePdf();
	}
}
