package api.exceptions;

public class CashierClosingNotFoundException extends ApiException {

	private static final long serialVersionUID = 5417661361405771382L;

	public static final String DESCRIPTION = "Cierre de caja no encontrado";

    public static final int CODE = 27;

    public CashierClosingNotFoundException() {
        this("");
    }

    public CashierClosingNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
}
