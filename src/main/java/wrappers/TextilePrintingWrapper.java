package wrappers;

public class TextilePrintingWrapper extends ProductWrapper{
    private String type;

    public TextilePrintingWrapper(){
        super();
    }
    
    public TextilePrintingWrapper(String type) {
        super();
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TextilePrintingWrapper [type=" + type + "]";
    }
     
}
