package api.exceptions;

public class ArticleNotFoundException extends ApiException {
    private static final long serialVersionUID = -5801317295531432731L;

    private static final String DESCRIPTION = "Código de ticket no encontrado";

    private static final int CODE = 30;

    public ArticleNotFoundException() {
        this("");
    }

    public ArticleNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
