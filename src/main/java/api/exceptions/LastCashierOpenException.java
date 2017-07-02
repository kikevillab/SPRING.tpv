package api.exceptions;

public class LastCashierOpenException extends ApiException {

	private static final long serialVersionUID = -1087223939569524961L;

	public static final String DESCRIPTION = "No se puede crear una nueva caja si la yltima caja no esta cerrada";

    public static final int CODE = 25;

    public LastCashierOpenException() {
        this("");
    }

    public LastCashierOpenException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }


}
