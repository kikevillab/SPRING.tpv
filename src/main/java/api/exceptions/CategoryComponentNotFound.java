package api.exceptions;

public class CategoryComponentNotFound extends ApiException {

    private static final long serialVersionUID = -4101618007964666528L;

    private static final String DESCRIPTION = "Category not found. ";

    private static final int CODE = 28;

    public CategoryComponentNotFound() {
        this("");
    }
    public CategoryComponentNotFound(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
    

}
