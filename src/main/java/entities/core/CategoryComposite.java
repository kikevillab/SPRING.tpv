package entities.core;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class CategoryComposite extends CategoryComponent {

    @OneToMany(fetch = FetchType.EAGER)
    private List<CategoryComponent> categoryComponents;

    public CategoryComposite() {
        super();
        categoryComponents = new ArrayList<>();
    }

    public CategoryComposite(String code, String name) {
        super(code, name);
        categoryComponents = new ArrayList<>();
    }

    public List<CategoryComponent> getCategoryComponents() {
        return categoryComponents;
    }

    public void setCategoryComponents(List<CategoryComponent> categoryComponents) {
        this.categoryComponents = categoryComponents;
    }

    public void addCategoryComponent(CategoryComponent categoryComponent) {
        this.categoryComponents.add(categoryComponent);
    }

    public void removeCategoryComponent(CategoryComponent categoryComponent) {
        this.categoryComponents.remove(categoryComponent);
    }

    @Override
    public List<CategoryComponent> components() {
        return this.categoryComponents;
    }

    @Override
    public void addComponent(CategoryComponent component) {
        this.categoryComponents.add(component);
    }

    @Override
    public Product product() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return super.toString() + "CategoryComposite [categoryComponents=" + categoryComponents + "]";
    }
}
