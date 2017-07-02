package api.exceptions;

/**
 * Created by Eneas on 14/05/2017.
 */
public class LastCashierClosedException extends ApiException {

	private static final long serialVersionUID = 1164995676613334440L;

	public static final String DESCRIPTION = "No se puede cerrar una caja ya cerrada";

    public static final int CODE = 26;

    public LastCashierClosedException() {
        this("");
    }

    public LastCashierClosedException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
