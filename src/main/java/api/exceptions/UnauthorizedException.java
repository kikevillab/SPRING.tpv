package api.exceptions;

public class UnauthorizedException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Identification utilised hasn't got enough authority level";

    public static final int CODE = 5;

    public UnauthorizedException() {
        this("");
    }

    public UnauthorizedException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
