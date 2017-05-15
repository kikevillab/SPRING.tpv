package api.exceptions;

public class LastCashierClosureIsOpenYetException extends ApiException {

	private static final long serialVersionUID = -1087223939569524961L;

	public static final String DESCRIPTION = "Impossible to create an cashier closure when a last is opened yet";

    public static final int CODE = 25;

    public LastCashierClosureIsOpenYetException() {
        this("");
    }

    public LastCashierClosureIsOpenYetException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }


}
