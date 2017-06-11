package services;

import static config.ResourceNames.INVOICES_PDFS_ROOT;
import static config.ResourceNames.INVOICE_PDF_FILENAME_ROOT;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import entities.core.Invoice;
import entities.core.Shopping;
import entities.core.Ticket;

public class InvoicePdfGenerator extends PdfGenerator<Invoice> {
    private final static float[] SHOPPING_LIST_COLUMNS_WIDTHS = new float[] {10.0f, 30.0f, 30.0f, 10.0f, 40.0f, 30.0f};

    public InvoicePdfGenerator(Invoice entity) {
        super(entity);
    }

    @Override
    protected String path() {
        return INVOICES_PDFS_ROOT + INVOICE_PDF_FILENAME_ROOT + entity.getId();
    }

    @Override
    protected PageSize pageSize() {
        return PageSize.A4;
    }

    @Override
    protected PdfFont font() throws IOException {
        return document.getPdfDocument().getDefaultFont();
    }

    @Override
    protected float fontSize() {
        return 5;
    }

    @Override
    protected HorizontalAlignment horizontalAlignment() {
        return HorizontalAlignment.CENTER;
    }

    @Override
    protected TextAlignment textAlignment() {
        return TextAlignment.LEFT;
    }

    @Override
    protected float leftMargin() {
        return 0;
    }

    @Override
    protected float rightMargin() {
        return 0;
    }

    @Override
    protected float topMargin() {
        return 0;
    }

    @Override
    protected float bottomMargin() {
        return 0;
    }

    @Override
    protected void buildPdf() {
        document.add(new Paragraph(INVOICE_PDF_FILENAME_ROOT + entity.getId()));
        Ticket ticket = entity.getTicket();
        document.add(new Paragraph("====================== Ticket ======================"));
        document.add(new Paragraph("Reference: " + ticket.getReference()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        document.add(new Paragraph("Created on: " + formatter.format(ticket.getCreated().getTime())));
        document.add(new Paragraph("Shopping list:"));
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
        document.add(shoppingListTable);
    }

}
