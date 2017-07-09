package utils.pdfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import config.ResourceNames;

public class PdfBuilder {

    private static final String USER_HOME = "user.home";

    private static final String ROOT_PDFS = "/tpv/pdfs";

    private static final String PDF_FILE_EXT = ".pdf";

    private static final int TERMIC_FONT_SIZE = 7;

    private static final int TERMIC_FONT_SIZE_EMPHASIZEDD = 9;

    private static final int TERMIC_MARGIN = 4;

    private static final float TERMIC_PAGE_WIDHT = 227;

    private static final float TERMIC_PAGE_HEIGHT = 600;

    private static final float A4_FONT_SIZE = 11;

    private static final int IMAGE_WIDTH = 80;

    private static final float LINE_WIDTH = 0.5f;

    private static final int LINE_GAP = 2;

    private static final int LINE_DASH = 1;

    private String fullPath;

    private Document document;

    private Table table;

    public PdfBuilder(String path) {
        fullPath = System.getProperty(USER_HOME) + ROOT_PDFS + path + PDF_FILE_EXT;
        this.prepareDocument(PageSize.A4);
    }

    private void prepareDocument(PageSize pageSize) {
        File file = new File(fullPath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            document = new Document(new PdfDocument(new PdfWriter(fullPath)), pageSize);
        } catch (FileNotFoundException fnfe) {
            LogManager.getLogger().error("File: " + fnfe);
            fnfe.printStackTrace();
        }
    }

    public PdfBuilder pageTermic() {
        this.prepareDocument(new PageSize(TERMIC_PAGE_WIDHT, TERMIC_PAGE_HEIGHT));
        document.setMargins(TERMIC_MARGIN, TERMIC_MARGIN, TERMIC_MARGIN, TERMIC_MARGIN);
        document.setFontSize(TERMIC_FONT_SIZE);
        document.setTextAlignment(TextAlignment.LEFT);
        return this;
    }

    public PdfBuilder pageA4() {
        this.prepareDocument(PageSize.A4);
        document.setFontSize(A4_FONT_SIZE);
        document.setTextAlignment(TextAlignment.LEFT);
        return this;
    }

    private String absolutePathOfResource(String resource) {
        URL resourceURL = getClass().getClassLoader().getResource(resource);
        try {
            return Paths.get(resourceURL.toURI()).toFile().getAbsolutePath();
        } catch (URISyntaxException use) {
            LogManager.getLogger().error("URI: " + use);
        }
        return resource;
    }

    public PdfBuilder addImage(String fileName) {
        try {
            Image img = new Image(ImageDataFactory.create(this.absolutePathOfResource(ResourceNames.IMAGES + fileName)));
            img.setWidth(IMAGE_WIDTH);
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);
        } catch (MalformedURLException mue) {
            LogManager.getLogger().error("File: " + mue);
        }
        return this;

    }

    public PdfBuilder paragraph(String text) {
        document.add(new Paragraph(text));
        return this;
    }

    public PdfBuilder paragraphEmphasized(String text) {
        document.add(new Paragraph(text).setBold().setFontSize(TERMIC_FONT_SIZE_EMPHASIZEDD));
        return this;
    }

    public PdfBuilder separator() {
        CustomDashedLineSeparator separator = new CustomDashedLineSeparator();
        separator.setDash(LINE_DASH);
        separator.setGap(LINE_GAP);
        separator.setLineWidth(LINE_WIDTH);
        document.add(new LineSeparator(separator));
        return this;
    }

    public PdfBuilder valuesTable(float... widths) {
        table = new Table(widths, true);
        table.setBorder(new SolidBorder(Color.WHITE, 2));
        table.setVerticalAlignment(VerticalAlignment.MIDDLE);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setTextAlignment(TextAlignment.RIGHT);
        return this;
    }

    public PdfBuilder tableHeader(String... headers) {
        for (String header : headers) {
            table.addHeaderCell(header);
        }
        return this;
    }

    public PdfBuilder tableCell(String... cells) {
        for (String cell : cells) {
            table.addCell(cell);
        }
        return this;
    }

    public PdfBuilder tableColspanRight(String value) {
        Cell cell = new Cell(1, table.getNumberOfColumns());
        cell.setTextAlignment(TextAlignment.RIGHT).setBold().setFontSize(TERMIC_FONT_SIZE_EMPHASIZEDD);
        cell.add(value);
        table.addCell(cell);
        document.add(table);
        return this;
    }

    public PdfBuilder header(String logoFileName, String header) {
        Table table = new Table(new float[] {1, 2}, true);
        table.setBorder(Border.NO_BORDER);
        try {
            Image img = new Image(ImageDataFactory.create(this.absolutePathOfResource(ResourceNames.IMAGES + logoFileName)));
            img.setWidth(50);
            img.setHorizontalAlignment(HorizontalAlignment.LEFT);
            table.addCell(new Cell().add(img).setBorder(Border.NO_BORDER));
        } catch (MalformedURLException mue) {
            LogManager.getLogger().error("File: " + mue);
        }
        Paragraph paragraph = new Paragraph();
        paragraph.setTextAlignment(TextAlignment.RIGHT);
        paragraph.setBold();
        paragraph.add(header);
        table.addCell(new Cell().add(paragraph).setBorder(Border.NO_BORDER));
        document.add(table);
        return this;
    }
    
    public PdfBuilder header(String header){
        Paragraph paragraph = new Paragraph(header);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setBold();
        paragraph.setFontSize(TERMIC_FONT_SIZE_EMPHASIZEDD);
        document.add(paragraph);
        return this;
    }

    public PdfBuilder barCode(String code) {
        Barcode128 code128 = new Barcode128(document.getPdfDocument());
        code128.setCodeType(Barcode128.CODE128);
        code128.setCode(code.trim());
        Image code128Image = new Image(code128.createFormXObject(document.getPdfDocument()));
        code128Image.setWidthPercent(50);
        code128Image.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(code128Image);
        return this;
    }

    public PdfBuilder qrCode(String code) {
        BarcodeQRCode qrcode = new BarcodeQRCode(code.trim());
        Image qrcodeImage = new Image(qrcode.createFormXObject(document.getPdfDocument()));
        qrcodeImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
        qrcodeImage.setWidthPercent(50);
        document.add(qrcodeImage);
        Paragraph paragraph = new Paragraph("Ref. " + code);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph);
        return this;
    }

    public byte[] build() {
        document.close();
        try {
            return Files.readAllBytes(new File(fullPath).toPath());
        } catch (IOException ioe) {
            LogManager.getLogger().error("IO: " + ioe);
        }
        return null;
    }

}
