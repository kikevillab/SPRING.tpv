package api.exceptions;

public class VoucherAlreadyConsumedException extends ApiException {

    private static final long serialVersionUID = -7439666619819762541L;

    public static final String DESCRIPTION = "Vale ya ha sido consumado";

    public static final int CODE = 23;

    public VoucherAlreadyConsumedException() {
        this("");
    }

    public VoucherAlreadyConsumedException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
}
