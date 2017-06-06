package wrappers;

import entities.core.CategoryComponent;

public class CategoryComponentWrapper {
    private String code;

    private String name;

    private String image;

    public CategoryComponentWrapper() {
        super();
    }

    public CategoryComponentWrapper(CategoryComponent component) {
        this.code = component.getCode();
        this.name = component.getName();
        this.image = component.getIcon();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CategoryComponentWrapper [code=" + code + ", name=" + name + ", image=" + image + "]";
    }

}
