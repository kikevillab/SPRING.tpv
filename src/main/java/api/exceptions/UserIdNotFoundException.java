package api.exceptions;

public class UserIdNotFoundException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Id de usuario no encontrado";

    public static final int CODE = 4;

    public UserIdNotFoundException() {
        this("");
    }

    public UserIdNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
