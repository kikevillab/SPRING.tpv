package api.exceptions;

public class InvalidProductAmountInUpdateTicketException extends ApiException {
    private static final long serialVersionUID = 6970924166259598343L;

    public static final String DESCRIPTION = "Cantidad de producto inferior a 0 en modificación de ticket";

    public static final int CODE = 13;

    public InvalidProductAmountInUpdateTicketException() {
        this("");
    }

    public InvalidProductAmountInUpdateTicketException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
