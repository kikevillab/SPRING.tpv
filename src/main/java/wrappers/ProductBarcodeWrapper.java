package wrappers;

public class ProductBarcodeWrapper {
    String barcode;

    public ProductBarcodeWrapper() {
    }

    public ProductBarcodeWrapper(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "ProductBarcodeWrapper [barcode=" + barcode + "]";
    }

}
