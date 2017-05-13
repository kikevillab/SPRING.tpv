package api.exceptions;

public class TicketHasInvalidUserException extends ApiException {

    private static final long serialVersionUID = -4328051169500985249L;
    
    public static final String DESCRIPTION = "Invalid user in ticket";

    public static final int CODE = 19;
    
    public TicketHasInvalidUserException() {
        this("");
    }
    
    public TicketHasInvalidUserException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
   
}
