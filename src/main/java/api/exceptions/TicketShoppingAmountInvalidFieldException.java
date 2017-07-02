package api.exceptions;

public class TicketShoppingAmountInvalidFieldException extends ApiException {
    private static final long serialVersionUID = 6970924166259598343L;

    public static final String DESCRIPTION = "Valor de cantidad en compra de ticket debe ser mayor que 0";

    public static final int CODE = 12;

    public TicketShoppingAmountInvalidFieldException() {
        this("");
    }

    public TicketShoppingAmountInvalidFieldException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
