package api.exceptions;

public class InvalidProductAmountInUpdateTicketException extends ApiException {
    private static final long serialVersionUID = 6970924166259598343L;

    public static final String DESCRIPTION = "Product amount is less than 0 in ticket update";

    public static final int CODE = 13;

    public InvalidProductAmountInUpdateTicketException() {
        this("");
    }

    public InvalidProductAmountInUpdateTicketException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
