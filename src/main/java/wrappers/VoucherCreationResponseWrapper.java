package wrappers;

import java.util.Arrays;

public class VoucherCreationResponseWrapper {
    int voucherId;

    byte[] pdfByteArray;

    public VoucherCreationResponseWrapper() {
    }

    public VoucherCreationResponseWrapper(int voucherId, byte[] pdfByteArray) {
        super();
        this.voucherId = voucherId;
        this.pdfByteArray = pdfByteArray;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public byte[] getPdfByteArray() {
        return pdfByteArray;
    }

    public void setPdfByteArray(byte[] pdfByteArray) {
        this.pdfByteArray = pdfByteArray;
    }

    @Override
    public String toString() {
        return "VoucherCreationResponseWrapper [voucherId=" + voucherId + ", pdfByteArray=" + Arrays.toString(pdfByteArray) + "]";
    }

}
