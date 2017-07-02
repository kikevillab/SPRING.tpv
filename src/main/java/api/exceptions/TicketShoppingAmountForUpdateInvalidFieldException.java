package api.exceptions;

public class TicketShoppingAmountForUpdateInvalidFieldException extends ApiException {
    private static final long serialVersionUID = 6970924166259598343L;

    public static final String DESCRIPTION = "Valor de cantidad en compra de ticket debe ser mayor o igual a 0";

    public static final int CODE = 13;

    public TicketShoppingAmountForUpdateInvalidFieldException() {
        this("");
    }

    public TicketShoppingAmountForUpdateInvalidFieldException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
