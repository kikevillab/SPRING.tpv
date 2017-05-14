package api.exceptions;

/**
 * Created by Eneas on 14/05/2017.
 */
public class LastCashierClosureIsClosedException extends ApiException {
    public static final String DESCRIPTION = "Impossible to close cashier when is already closed";

    public static final int CODE = 26;

    public LastCashierClosureIsClosedException() {
        this("");
    }

    public LastCashierClosureIsClosedException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
