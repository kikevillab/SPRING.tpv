package api.exceptions;

public class TicketNotFoundException extends ApiException {

    private static final long serialVersionUID = -9070950214395814630L;

    public static final String DESCRIPTION = "Ticket no encontrado.";

    public static final int CODE = 404;
    
    public TicketNotFoundException(){
        this("");
    }
    
    public TicketNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
