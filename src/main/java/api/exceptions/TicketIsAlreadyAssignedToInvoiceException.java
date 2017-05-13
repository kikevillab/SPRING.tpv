package api.exceptions;

public class TicketIsAlreadyAssignedToInvoiceException extends ApiException {

    private static final long serialVersionUID = 7651290393491101610L;

    public static final String DESCRIPTION = "This ticket has already been assigned to an invoice";

    public static final int CODE = 20;
    
    public TicketIsAlreadyAssignedToInvoiceException() {
        this("");
    }
    
    public TicketIsAlreadyAssignedToInvoiceException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
}
