package api.exceptions;

public class InvalidProductAmountInNewTicketException extends ApiException {
    private static final long serialVersionUID = 6970924166259598343L;

    public static final String DESCRIPTION = "Cantidad de producto igual o inferior a 0 en nuevo ticket";

    public static final int CODE = 12;

    public InvalidProductAmountInNewTicketException() {
        this("");
    }

    public InvalidProductAmountInNewTicketException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
