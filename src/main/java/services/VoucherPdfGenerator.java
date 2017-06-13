package services;

import static config.ResourceNames.VOUCHERS_PDFS_ROOT;
import static config.ResourceNames.VOUCHER_PDF_FILENAME_ROOT;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import config.ResourceNames;
import entities.core.Voucher;

public class VoucherPdfGenerator extends PdfGenerator<Voucher> {

    public VoucherPdfGenerator(Voucher entity) {
        super(entity);
    }

    @Override
    protected String path() {
        return VOUCHERS_PDFS_ROOT + VOUCHER_PDF_FILENAME_ROOT + entity.getId();
    }

    @Override
    protected PageSize pageSize() {
        return PageSize.A5;
    }

    @Override
    protected PdfFont font() throws IOException, URISyntaxException {
        String fontPath = getAbsolutePathOfResource(ResourceNames.FONTS, ResourceNames.FAKE_RECEIPT_REGULAR_FONT);
        return PdfFontFactory.createFont(fontPath, PdfEncodings.CP1250, true);
    }

    @Override
    protected void buildPdf() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        document.add(new Paragraph("REFERENCE: " + entity.getReference()));
        document.add(new Paragraph("VALUE: " + entity.getValue().toPlainString()));
        document.add(new Paragraph("CREATED ON: " + formatter.format(entity.getCreated().getTime())));
        document.add(new Paragraph("CONSUME VOUCHER BEFORE: " + formatter.format(entity.getExpiration().getTime())));
        String canjeado = "NOT CONSUMED YET";
        if (entity.getDateOfUse() != null) {
            canjeado = formatter.format(entity.getDateOfUse().getTime());
        }
        document.add(new Paragraph("CONSUMED: " + canjeado));
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

}
