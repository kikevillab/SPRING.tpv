package api.exceptions;

public class VoucherHasExpiredException extends ApiException {
    
    private static final long serialVersionUID = -3987169339440577373L;

    public static final String DESCRIPTION = "Vale caducado";

    public static final int CODE = 24;

    public VoucherHasExpiredException() {
        this("");
    }

    public VoucherHasExpiredException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
}
