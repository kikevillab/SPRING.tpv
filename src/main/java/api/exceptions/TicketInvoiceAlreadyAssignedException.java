package api.exceptions;

public class TicketInvoiceAlreadyAssignedException extends ApiException {

    private static final long serialVersionUID = 7651290393491101610L;

    public static final String DESCRIPTION = "El ticket ya tiene asignado una factura";

    public static final int CODE = 20;
    
    public TicketInvoiceAlreadyAssignedException() {
        this("");
    }
    
    public TicketInvoiceAlreadyAssignedException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
}
