package api.exceptions;

public class VoucherNotFoundException extends ApiException {
    
    private static final long serialVersionUID = 3949949430958694740L;

    public static final String DESCRIPTION = "Vale no encontrado";

    public static final int CODE = 22;

    public VoucherNotFoundException() {
        this("");
    }

    public VoucherNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
}
