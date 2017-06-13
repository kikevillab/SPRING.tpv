package services;

import static config.ResourceNames.INVOICES_PDFS_ROOT;
import static config.ResourceNames.INVOICE_PDF_FILENAME_ROOT;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import config.ResourceNames;
import entities.core.Invoice;
import entities.core.Shopping;
import entities.core.Ticket;
import entities.users.User;

public class InvoicePdfGenerator extends PdfGenerator<Invoice> {

    private final static float[] SHOPPING_LIST_COLUMNS_WIDTHS = new float[] {200, 1, 1, 1, 1, 1};

    private final static float[] TABLE_COLUMNS_WIDTHS = new float[] {1, 1};

    private final static SimpleDateFormat invoiceCreationDateFormatter = new SimpleDateFormat("MMM dd, yyyy");

    private final static SimpleDateFormat ticketCreationDateFormatter = new SimpleDateFormat("MMM dd, yyyy - HH:mm");

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
    protected PdfFont font() throws IOException, URISyntaxException{
        String fontPath = getAbsolutePathOfResource(ResourceNames.FONTS, ResourceNames.OPEN_SANS_REGULAR_FONT);
        return PdfFontFactory.createFont(fontPath);
    }

    @Override
    protected float fontSize() {
        return 12;
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
        return 15;
    }

    @Override
    protected float rightMargin() {
        return 15;
    }

    @Override
    protected float topMargin() {
        return 15;
    }

    @Override
    protected float bottomMargin() {
        return 15;
    }

    @Override
    protected void buildPdf() {
        Table invoiceHeader = new Table(TABLE_COLUMNS_WIDTHS, true);
        try {
            Image img = new Image(ImageDataFactory.create(getAbsolutePathOfResource(ResourceNames.IMAGES, ResourceNames.UPM_LOGO)));
            img.setWidth(50);
            img.setHorizontalAlignment(HorizontalAlignment.LEFT);
            invoiceHeader.addCell(new Cell().add(img).setBorder(Border.NO_BORDER));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Paragraph rightAlignedParagraph = new Paragraph();
        rightAlignedParagraph.setTextAlignment(TextAlignment.RIGHT);
        rightAlignedParagraph.add("INVOICE " + entity.getId() + "\n");
        rightAlignedParagraph.add(invoiceCreationDateFormatter.format(entity.getCreated().getTime()));
        invoiceHeader.addCell(new Cell().add(rightAlignedParagraph).setBorder(Border.NO_BORDER));
        document.add(invoiceHeader);
        Ticket ticket = entity.getTicket();
        User user = ticket.getUser();
        Cell noBorderCell = new Cell().setBorder(Border.NO_BORDER);
        noBorderCell.setTextAlignment(TextAlignment.LEFT);
        noBorderCell.add("From:").add("Campus Sur UPM").add("Calle Nikola Tesla, s/n").add("28031 Madrid");
        Table info = new Table(TABLE_COLUMNS_WIDTHS, true);
        info.addCell(noBorderCell);
        noBorderCell = noBorderCell.clone(false);
        noBorderCell.add("To:").add(user.getUsername()).add(user.getDni()).add(user.getEmail()).add(user.getAddress());
        info.addCell(noBorderCell);
        document.add(info);

        Paragraph ticketDetails = new Paragraph().add("TICKET DETAILS").add("\nReference: ").add(ticket.getReference())
                .add("\nCreaten on: ").add(ticketCreationDateFormatter.format(ticket.getCreated().getTime()));
        document.add(ticketDetails);
        Table shoppingListTable = new Table(SHOPPING_LIST_COLUMNS_WIDTHS, true);
        shoppingListTable.addCell("Item");
        shoppingListTable.addCell("Price");
        shoppingListTable.addCell("Quantity");
        shoppingListTable.addCell("Subtotal");
        shoppingListTable.addCell("Discount");
        shoppingListTable.addCell("Total");
        for (Shopping shopping : ticket.getShoppingList()) {
            shoppingListTable.addCell(String.valueOf(shopping.getDescription()));
            shoppingListTable
                    .addCell(new Cell().add(String.valueOf(shopping.getRetailPrice() + "€")).setTextAlignment(TextAlignment.RIGHT));
            shoppingListTable.addCell(new Cell().add(String.valueOf(shopping.getAmount())).setTextAlignment(TextAlignment.RIGHT));
            shoppingListTable
                    .addCell(new Cell().add(roundedValue(shopping.getShoppingSubtotal()) + "€").setTextAlignment(TextAlignment.RIGHT));
            shoppingListTable.addCell(new Cell().add(String.valueOf(shopping.getDiscount())).setTextAlignment(TextAlignment.RIGHT));
            shoppingListTable
                    .addCell(new Cell().add(roundedValue(shopping.getShoppingTotal()) + "€").setTextAlignment(TextAlignment.RIGHT));
        }
        document.add(shoppingListTable);
        document.add(new Paragraph().setTextAlignment(TextAlignment.RIGHT).add("Total: ")
                .add(roundedValue(ticket.getTicketTotal().doubleValue())));
    }

    private String roundedValue(double value) {
        DecimalFormat decFormat = new DecimalFormat("#.##");
        return decFormat.format(value);
    }

}
