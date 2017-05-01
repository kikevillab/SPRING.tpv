package api.exceptions;

public class InvoiceNotFoundException extends ApiException{

    private static final long serialVersionUID = 8857826753529436897L;

    public static final String DESCRIPTION = "Factura no encontrada.";

    public static final int CODE = 404;
    
    public InvoiceNotFoundException(){
        this("");
    }
    
    public InvoiceNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
