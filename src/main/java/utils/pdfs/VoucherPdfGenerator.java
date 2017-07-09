package utils.pdfs;

import static config.ResourceNames.VOUCHERS_PDFS_ROOT;
import static config.ResourceNames.VOUCHER_PDF_FILENAME_ROOT;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import config.ResourceNames;
import entities.core.Voucher;

public class VoucherPdfGenerator extends PdfGenerator<Voucher> {

    private final static float VOUCHER_PAGE_WIDHT = 227;

    private final static float VOUCHER_PAGE_HEIGHT = 842;
    
    public VoucherPdfGenerator(Voucher entity) {
        super(entity);
    }

    @Override
    protected String path() {
        return VOUCHERS_PDFS_ROOT + VOUCHER_PDF_FILENAME_ROOT + entity.getId();
    }

    @Override
    protected PageSize pageSize() {
        return new PageSize(VOUCHER_PAGE_WIDHT, VOUCHER_PAGE_HEIGHT);
    }

    @Override
    protected PdfFont font() throws IOException, URISyntaxException {
        String fontPath = getAbsolutePathOfResource(ResourceNames.FONTS, ResourceNames.FAKE_RECEIPT_REGULAR_FONT);
        return PdfFontFactory.createFont(fontPath, PdfEncodings.CP1250, true);
    }

    @Override
    protected void buildPdf() {
        try {
            Image img = new Image(ImageDataFactory.create(getAbsolutePathOfResource(ResourceNames.IMAGES, ResourceNames.UPM_LOGO)));
            img.setWidth(50);
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);         
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        CustomDashedLineSeparator separator = new CustomDashedLineSeparator();
        separator.setDash(1);
        separator.setGap(2);
        separator.setLineWidth(0.5f);      
        document.add(new Paragraph("Campus Sur UPM\nCalle Nikola Tesla, s/n\n28031 Madrid").setTextAlignment(TextAlignment.CENTER));
        document.add(new LineSeparator(separator));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        document.add(new Paragraph("REFERENCE: " + entity.getReference()));
        document.add(new Paragraph(entity.getValue().setScale(2, RoundingMode.HALF_UP).toPlainString() + "€").setFontSize(30).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("CREATED ON: " + formatter.format(entity.getCreated().getTime())));
        document.add(new Paragraph("CONSUME VOUCHER BEFORE: " + formatter.format(entity.getExpiration().getTime())));
    }

    @Override
    protected float fontSize() {
        return 5;
    }

    @Override
    protected HorizontalAlignment horizontalAlignment() {
        return HorizontalAlignment.RIGHT;
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

}
