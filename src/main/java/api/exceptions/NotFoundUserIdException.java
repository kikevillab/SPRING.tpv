package api.exceptions;

public class NotFoundUserIdException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "User id provided not found";

    public static final int CODE = 4;

    public NotFoundUserIdException() {
        this("");
    }

    public NotFoundUserIdException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
