package wrappers;

import java.util.Arrays;

public class InvoiceCreationResponseWrapper {

    private int invoiceId;

    private byte[] pdfByteArray;

    public InvoiceCreationResponseWrapper() {
    }

    public InvoiceCreationResponseWrapper(int invoiceId, byte[] pdfByteArray) {
        this.invoiceId = invoiceId;
        this.pdfByteArray = pdfByteArray;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public byte[] getPdfByteArray() {
        return pdfByteArray;
    }

    public void setPdfByteArray(byte[] pdfByteArray) {
        this.pdfByteArray = pdfByteArray;
    }

    @Override
    public String toString() {
        return "InvoiceCreationResponseWrapepr [invoiceId=" + invoiceId + ", pdfByteArray=" + Arrays.toString(pdfByteArray) + "]";
    }
}
