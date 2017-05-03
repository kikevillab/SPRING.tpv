package services;

import static config.ResourceNames.INVOICES_PDFS_ROOT;
import static config.ResourceNames.INVOICE_PDF_FILENAME_ROOT;

import java.text.SimpleDateFormat;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import entities.core.Invoice;
import entities.core.Shopping;
import entities.core.Ticket;

public class InvoicePdfGenerator extends PdfGenerator<Invoice> {
    private final static float[] SHOPPING_LIST_COLUMNS_WIDTHS = new float[] { 10.0f, 30.0f, 30.0f, 10.0f, 40.0f,
            30.0f };	
    
    public InvoicePdfGenerator(Invoice entity) {
        super(entity);
    }

	@Override
	protected String ownPath() {
		return INVOICES_PDFS_ROOT + INVOICE_PDF_FILENAME_ROOT + entity.getId();
	}

	@Override
	protected PageSize ownPageSize() {
		return PageSize.A4;
	}

    @Override
    protected void buildPdf() {
        pdfDocument.add(new Paragraph(INVOICE_PDF_FILENAME_ROOT + entity.getId()));
        Ticket ticket = entity.getTicket();
        pdfDocument.add(new Paragraph("====================== Ticket ======================"));
        pdfDocument.add(new Paragraph("Reference: " + ticket.getReference()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        pdfDocument.add(new Paragraph("Created on: " + formatter.format(ticket.getCreated().getTime())));
        pdfDocument.add(new Paragraph("Shopping list:"));
        Table shoppingListTable = new Table(SHOPPING_LIST_COLUMNS_WIDTHS);
        shoppingListTable.addCell("Id");
        shoppingListTable.addCell("Amount");
        shoppingListTable.addCell("Discount");
        shoppingListTable.addCell("ProductId");
        shoppingListTable.addCell("Description");
        shoppingListTable.addCell("Retail price");
        for (Shopping shopping : ticket.getShoppingList()) {
            shoppingListTable.addCell(String.valueOf(shopping.getId()));
            shoppingListTable.addCell(String.valueOf(shopping.getAmount()));
            shoppingListTable.addCell(String.valueOf(shopping.getDiscount() + "%"));
            shoppingListTable.addCell(String.valueOf(shopping.getProduct().getDescription()));
            shoppingListTable.addCell(String.valueOf(shopping.getDescription()));
            shoppingListTable.addCell(String.valueOf(shopping.getRetailPrice() + "€"));
        }
        pdfDocument.add(shoppingListTable);        
    }
}
