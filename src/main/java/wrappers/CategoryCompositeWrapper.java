package wrappers;

import java.util.ArrayList;
import java.util.List;

import entities.core.CategoryComponent;

public class CategoryCompositeWrapper extends CategoryComponentWrapper {
    private List<CategoryComponentWrapper> components;

    public CategoryCompositeWrapper() {
        super();
        components = new ArrayList<>();
    }

    public CategoryCompositeWrapper(CategoryComponent categoryComponent) {
        super(categoryComponent);
        components = new ArrayList<>();
        categoryComponent.components().forEach(component -> this.components.add(new CategoryComponentWrapper(component)));
    }

    public List<CategoryComponentWrapper> getComponents() {
        return components;
    }

    public void setComponents(List<CategoryComponentWrapper> components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return super.toString() + "CategoryCompositeWrapper [components=" + components + "]";
    }
}
