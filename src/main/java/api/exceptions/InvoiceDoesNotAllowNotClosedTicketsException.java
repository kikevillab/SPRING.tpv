package api.exceptions;

public class InvoiceDoesNotAllowNotClosedTicketsException extends ApiException {

    private static final long serialVersionUID = -7943713780352854024L;

    public static final String DESCRIPTION = "Impossible to create an invoice of a not closed ticket";

    public static final int CODE = 17;
    
    public InvoiceDoesNotAllowNotClosedTicketsException() {
        this("");
    }
    
    public InvoiceDoesNotAllowNotClosedTicketsException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
}
