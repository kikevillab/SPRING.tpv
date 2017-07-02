package api.exceptions;

public class UserInvalidFieldException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Campo de usuario vacio o inexistente";

    public static final int CODE = 2;

    public UserInvalidFieldException() {
        this("");
    }

    public UserInvalidFieldException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
