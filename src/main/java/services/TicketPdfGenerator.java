package services;

import static config.ResourceNames.TICKETS_PDFS_ROOT;
import static config.ResourceNames.TICKET_PDF_FILENAME_ROOT;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import entities.core.Shopping;
import entities.core.Ticket;

public class TicketPdfGenerator extends PdfGenerator<Ticket> {
    // private final static float[] SHOPPING_LIST_COLUMNS_WIDTHS = new float[] {10.0f, 30.0f, 30.0f, 10.0f, 40.0f, 30.0f};

    private final static float[] SHOPPING_LIST_COLUMNS_WIDTHS = new float[] {1, 1, 1, 1, 1, 1};

    private final static float EIGHTY_MM = 227;

    private final static float QR_CODE_MODULE_SIZE = 2.5f;

    private final static String TICKET_FONT = "./src/main/resources/fonts/fake-receipt.regular.ttf";

    public TicketPdfGenerator(Ticket entity) {
        super(entity);
    }

    @Override
    protected String ownPath() {
        return TICKETS_PDFS_ROOT + TICKET_PDF_FILENAME_ROOT + entity.getId();
    }

    @Override
    protected PageSize ownPageSize() {
        return new PageSize(EIGHTY_MM, 842);
    }

    @Override
    protected void buildPdf() {
        try {
            document.setFont(PdfFontFactory.createFont(TICKET_FONT, PdfEncodings.CP1250, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.setTextAlignment(TextAlignment.CENTER);
        document.setFontSize(8.0f);
        document.setMargins(0.0f, 0.0f, 0.0f, 0.0f);
        document.add(new Paragraph("REFERENCE: " + entity.getReference()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        document.add(new Paragraph("CREATED ON: " + formatter.format(entity.getCreated().getTime())));
        Table shoppingListTable = new Table(SHOPPING_LIST_COLUMNS_WIDTHS);
        shoppingListTable.setBorder(new SolidBorder(Color.WHITE, 5));
        shoppingListTable.setVerticalAlignment(VerticalAlignment.MIDDLE);
        shoppingListTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
        shoppingListTable.addHeaderCell("");
        shoppingListTable.addHeaderCell("Amount".toUpperCase());
        shoppingListTable.addHeaderCell("%".toUpperCase());
        shoppingListTable.addHeaderCell("Product".toUpperCase());
        shoppingListTable.addHeaderCell("Description".toUpperCase());
        shoppingListTable.addHeaderCell("Price".toUpperCase());
        //entity.getShoppingList().addAll(entity.getShoppingList());
        //entity.getShoppingList().addAll(entity.getShoppingList());
        for (int i = 0; i < entity.getShoppingList().size(); i++) {
            Shopping shopping = entity.getShoppingList().get(i);
            shoppingListTable.addCell(String.valueOf(i+1));
            shoppingListTable.addCell(String.valueOf(shopping.getAmount()));
            shoppingListTable.addCell(String.valueOf(shopping.getDiscount() + "%"));
            shoppingListTable.addCell(String.valueOf(shopping.getProduct().getDescription()));
            shoppingListTable.addCell(String.valueOf(shopping.getDescription()));
            shoppingListTable.addCell(String.valueOf(shopping.getRetailPrice() + "€"));
        }
        document.add(shoppingListTable);

        document.add(new Cell().add("TICKET TRACKING"));
        document.add(qrCodeImage(entity.getReference(), document.getPdfDocument()));
    }

    private Image qrCodeImage(String reference, PdfDocument pdfDocument) {
        BarcodeQRCode qrCode = new BarcodeQRCode(reference);
        PdfFormXObject pdfFormXObject = qrCode.createFormXObject(Color.BLACK, QR_CODE_MODULE_SIZE, pdfDocument);
        Image qrCodeImage = new Image(pdfFormXObject);
        qrCodeImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
        qrCodeImage.setTextAlignment(TextAlignment.CENTER);
        return qrCodeImage;
    }
}
