package wrappers;

import java.util.Arrays;

public class TicketCreationResponseWrapper {
    private byte[] pdfByteArray;
    
    private long ticketId;

    public TicketCreationResponseWrapper(){}
    
    public TicketCreationResponseWrapper(byte[] pdfByteArray, long ticketId){
        this.pdfByteArray = pdfByteArray;
        this.ticketId = ticketId;
    }
    
    public byte[] getPdfByteArray() {
        return pdfByteArray;
    }

    public void setPdfByteArray(byte[] pdfByteArray) {
        this.pdfByteArray = pdfByteArray;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "TicketCreationResponseWrapper [pdfByteArray=" + Arrays.toString(pdfByteArray) + ", ticketId=" + ticketId + "]";
    }

}
