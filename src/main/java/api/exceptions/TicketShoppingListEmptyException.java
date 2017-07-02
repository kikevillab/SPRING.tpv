package api.exceptions;

public class TicketShoppingListEmptyException extends ApiException {
    private static final long serialVersionUID = 7862705985622778351L;

    public static final String DESCRIPTION = "Lista de compras de ticket no puede estar vacia";

    public static final int CODE = 8;

    public TicketShoppingListEmptyException() {
        this("");
    }

    public TicketShoppingListEmptyException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
