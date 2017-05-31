package wrappers;

import java.util.Arrays;

public class TicketCreationResponseWrapper {
    private byte[] pdfByteArray;
    
    private String ticketReference;

    public TicketCreationResponseWrapper(){}
    
    public TicketCreationResponseWrapper(byte[] pdfByteArray, String ticketReference){
        this.pdfByteArray = pdfByteArray;
        this.ticketReference = ticketReference;
    }
    
    public byte[] getPdfByteArray() {
        return pdfByteArray;
    }

    public void setPdfByteArray(byte[] pdfByteArray) {
        this.pdfByteArray = pdfByteArray;
    }

    public String getTicketReference() {
        return ticketReference;
    }

    public void setTicketReference(String ticketReference) {
        this.ticketReference = ticketReference;
    }

    @Override
    public String toString() {
        return "TicketCreationResponseWrapper [pdfByteArray=" + Arrays.toString(pdfByteArray) + ", ticketReference=" + ticketReference + "]";
    }

}
