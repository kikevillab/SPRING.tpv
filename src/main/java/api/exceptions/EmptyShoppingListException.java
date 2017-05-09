package api.exceptions;

public class EmptyShoppingListException extends ApiException {
    private static final long serialVersionUID = 7862705985622778351L;

    public static final String DESCRIPTION = "La lista de productos del ticket está vacía";

    public static final int CODE = 8;

    public EmptyShoppingListException() {
        this("");
    }

    public EmptyShoppingListException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
