package services;

import com.itextpdf.kernel.geom.PageSize;

import entities.core.Voucher;

public class VoucherPdfGenerator extends PdfGenerator<Voucher>{

    public VoucherPdfGenerator(Voucher voucher) {
        super(voucher);
    }

    @Override
    protected String ownPath() {
        return null;
    }

    @Override
    protected PageSize ownPageSize() {
        return null;
    }

    @Override
    protected void buildPdf() {
        
    }

}
