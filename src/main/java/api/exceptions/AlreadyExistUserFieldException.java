package api.exceptions;

public class AlreadyExistUserFieldException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "The mobile or email field already exists in database";

    public static final int CODE = 1;

    public AlreadyExistUserFieldException() {
        this("");
    }

    public AlreadyExistUserFieldException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
