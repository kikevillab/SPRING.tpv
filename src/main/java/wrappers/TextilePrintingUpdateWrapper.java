package wrappers;

public class TextilePrintingUpdateWrapper extends ProductWrapper{
    private String type;

    public TextilePrintingUpdateWrapper(){
        super();
    }

    public TextilePrintingUpdateWrapper(String type) {
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
        return "TextilePrintingUpdateWrapper [type=" + type + "]";
    }
}
