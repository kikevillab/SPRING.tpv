package api.exceptions;

public class NotEnoughStockException extends ApiException {

    private static final long serialVersionUID = -3273789534340515643L;

    public static final String DESCRIPTION = "No hay suficiente stock del artículo";

    public static final int CODE = 11;

    public NotEnoughStockException() {
        this("");
    }

    public NotEnoughStockException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
