package api.exceptions;

public class TicketIsAlreadyAssignedToInvoiceException extends ApiException {

    private static final long serialVersionUID = 7651290393491101610L;

    public static final String DESCRIPTION = "Este ticket ya está asignado a otra factura.";

    public static final int CODE = 404;
    
    public TicketIsAlreadyAssignedToInvoiceException() {
        this("");
    }
    
    public TicketIsAlreadyAssignedToInvoiceException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
}
