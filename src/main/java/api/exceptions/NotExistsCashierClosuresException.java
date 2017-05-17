package api.exceptions;

public class NotExistsCashierClosuresException extends ApiException {

	private static final long serialVersionUID = 5417661361405771382L;

	public static final String DESCRIPTION = "Not exists cashier clousures";

    public static final int CODE = 27;

    public NotExistsCashierClosuresException() {
        this("");
    }

    public NotExistsCashierClosuresException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
}
