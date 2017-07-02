package api.exceptions;

public class UserNotErasableException extends ApiException {

    public static final String DESCRIPTION = "No es posible eliminar el usuario";

    public static final int CODE = 99;

    private static final long serialVersionUID = -1344640670884805385L;

    public UserNotErasableException() {
        this("");
    }

    public UserNotErasableException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
