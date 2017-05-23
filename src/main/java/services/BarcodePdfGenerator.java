package services;

import static config.ResourceNames.BARCODES_PDFS_ROOT;
import static config.ResourceNames.BARCODE_PDF_FILENAME_ROOT;

import com.itextpdf.barcodes.BarcodeEAN;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;

import entities.core.Product;

public class BarcodePdfGenerator extends PdfGenerator<Product> {
    private final static float[] BARCODE_COLUMN_WIDTH = new float[] { 30.0f, 30.0f, 30.0f, 30.0f };

    public BarcodePdfGenerator(Product entity) {
        super(entity);
    }

    @Override
    protected String ownPath() {
        return BARCODES_PDFS_ROOT + BARCODE_PDF_FILENAME_ROOT + entity.getCode();
    }

    @Override
    protected PageSize ownPageSize() {
        return PageSize.A4;
    }

    @Override
    protected void buildPdf(PdfDocument pdfDocument, Document document) {
        Table table = new Table(BARCODE_COLUMN_WIDTH);
        for (int i = 0; i < 12; i++) {
            table.addCell(createBarcode(String.format("%08d", entity.getCode()), pdfDocument));
        }
        document.add(table);
        document.close();
    }
    
    public static Cell createBarcode(String code, PdfDocument pdfDocument) {
        BarcodeEAN barcode = new BarcodeEAN(pdfDocument);
        barcode.setCodeType(BarcodeEAN.EAN13);
        barcode.setCode(code);
        Cell cell = new Cell().add(new Image(barcode.createFormXObject(null, null, pdfDocument)));
        cell.setPaddingTop(10);
        cell.setPaddingRight(10);
        cell.setPaddingBottom(10);
        cell.setPaddingLeft(10);
        return cell;
    }

}
