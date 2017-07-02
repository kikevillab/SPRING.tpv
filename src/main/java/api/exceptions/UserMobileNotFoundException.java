package api.exceptions;

public class UserMobileNotFoundException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Movil de usuario no encontrado";

    public static final int CODE = 9;

    public UserMobileNotFoundException() {
        this("");
    }

    public UserMobileNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
