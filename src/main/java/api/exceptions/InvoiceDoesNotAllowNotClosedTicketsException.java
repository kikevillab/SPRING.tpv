package api.exceptions;

public class InvoiceDoesNotAllowNotClosedTicketsException extends ApiException {

    private static final long serialVersionUID = -7943713780352854024L;

    public static final String DESCRIPTION = "No se pueden crear facturas de tickets no cerrados.";

    public static final int CODE = 404;
    
    public InvoiceDoesNotAllowNotClosedTicketsException() {
        this("");
    }
    
    public InvoiceDoesNotAllowNotClosedTicketsException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
}
