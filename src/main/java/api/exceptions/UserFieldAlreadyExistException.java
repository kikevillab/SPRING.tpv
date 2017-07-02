package api.exceptions;

public class UserFieldAlreadyExistException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Movil o correo ya existente ";

    public static final int CODE = 1;

    public UserFieldAlreadyExistException() {
        this("");
    }

    public UserFieldAlreadyExistException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
