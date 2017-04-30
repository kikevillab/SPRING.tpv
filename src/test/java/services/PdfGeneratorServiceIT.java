package services;

import static config.ResourceNames.INVOICES_PDFS_ROOT;
import static config.ResourceNames.INVOICE_PDF_FILENAME_ROOT;
import static config.ResourceNames.PDFS_ROOT;
import static config.ResourceNames.PDF_FILE_EXT;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import daos.core.InvoiceDao;
import entities.core.Invoice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class PdfGeneratorServiceIT {

	@Autowired
	private PdfGeneratorService pdfGenService;

	@Autowired
	private InvoiceDao invoiceDao;

	@Test
	public void testGenerateInvoicePdf() throws FileNotFoundException {
		Invoice invoice = invoiceDao.findAll().get(0);
		pdfGenService.generateInvoicePdf(invoice);
		String path = PDFS_ROOT + INVOICES_PDFS_ROOT + INVOICE_PDF_FILENAME_ROOT + invoice.getId() + PDF_FILE_EXT;
		File pdfFile = new File(path);
		assertTrue(pdfFile.exists());
		assertTrue(pdfFile.canRead());
		assertTrue(pdfFile.canWrite());
		assertTrue(pdfFile.canExecute());
	}

}
