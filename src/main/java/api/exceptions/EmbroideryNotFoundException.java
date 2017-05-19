package api.exceptions;

public class EmbroideryNotFoundException extends ApiException {

    private static final long serialVersionUID = 7540534828581910301L;
    
    private static final String DESCRIPTION = "Embroidery not found.";

    private static final int CODE = 28;

    public EmbroideryNotFoundException() {
        this("");
    }

    public EmbroideryNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
}
