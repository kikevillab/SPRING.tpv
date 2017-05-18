package api.exceptions;

public class TextilePrintingNotFoundException extends ApiException {

    private static final long serialVersionUID = 2680802567302979160L;
    
    public static final String DESCRIPTION = "Textile printing not found.";

    public static final int CODE = 29;

    public TextilePrintingNotFoundException() {
        this("");
    }

    public TextilePrintingNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
