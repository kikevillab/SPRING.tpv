package api.exceptions;

public class NotFoundUserMobileException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "User mobile provided not found";

    public static final int CODE = 9;

    public NotFoundUserMobileException() {
        this("");
    }

    public NotFoundUserMobileException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
