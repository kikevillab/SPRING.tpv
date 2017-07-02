package api.exceptions;

public class TicketShoppingDiscountInvalidFieldException extends ApiException {
    private static final long serialVersionUID = -6099784745820921879L;

    public static final String DESCRIPTION = "El valor de descuento debe estar entre 0 y 100 %";

    public static final int CODE = 15;

    public TicketShoppingDiscountInvalidFieldException() {
        this("");
    }

    public TicketShoppingDiscountInvalidFieldException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
