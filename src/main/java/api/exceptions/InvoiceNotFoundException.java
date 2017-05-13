package api.exceptions;

public class InvoiceNotFoundException extends ApiException{

    private static final long serialVersionUID = 8857826753529436897L;

    public static final String DESCRIPTION = "Invoice not found";

    public static final int CODE = 18;
    
    public InvoiceNotFoundException(){
        this("");
    }
    
    public InvoiceNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
