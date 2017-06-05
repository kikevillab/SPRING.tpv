package wrappers;

import java.util.ArrayList;
import java.util.List;

import entities.core.CategoryComponent;
import entities.core.ProductCategory;

public class CategoryCompositeWrapper extends CategoryComponentWrapper {
    private List<CategoryComponentWrapper> components;

    public CategoryCompositeWrapper() {
        super();
        components = new ArrayList<>();
    }

    public CategoryCompositeWrapper(List<CategoryComponent> components) {
        this();
        for(CategoryComponent component : components){
            this.setCode(component.getCode());
            this.setImage(component.getIcon());
            this.setName(component.getName());
            if(component.isCategory()){
                this.components.add(new CategoryCompositeWrapper(component.components()));
            } else {
                this.components.add(new ProductCategoryWrapper((ProductCategory) component));
            }
        }
    }
    
    public CategoryCompositeWrapper(CategoryComponent component){
        this(component.components());
    }

    @Override
    public String toString() {
        return "CategoryCompositeWrapper [components=" + components + "]";
    }
}