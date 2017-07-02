package api.exceptions;

public class TicketProductCodeNotFoundException extends ApiException {

    private static final long serialVersionUID = 8019654584133930287L;

    public static final String DESCRIPTION = "Código de producto no encontrado";

    public static final int CODE = 14;

    public TicketProductCodeNotFoundException() {
        this("");
    }

    public TicketProductCodeNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
