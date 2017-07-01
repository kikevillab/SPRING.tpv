package entities.core;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class CategoryComposite extends CategoryComponent {

    @ManyToMany(fetch = FetchType.EAGER)
    private List<CategoryComponent> categoryComponents;

    public CategoryComposite() {
        this("");
        this.setCode(null);
    }

    public CategoryComposite(String name) {
        this.setName(name);
        categoryComponents = new ArrayList<>();
    }

    public void setCategoryComponents(List<CategoryComponent> categoryComponents) {
        this.categoryComponents = categoryComponents;
    }

    @Override
    public List<CategoryComponent> components() {
        return this.categoryComponents;
    }

    @Override
    public void addComponent(CategoryComponent component) {
        this.categoryComponents.add(component);
    }
    
    public void addComponents(List<CategoryProduct> productCategoryExpandedList) {
        this.categoryComponents.addAll(productCategoryExpandedList);
    }
    
    public void removeComponent(CategoryComponent component){
        this.categoryComponents.remove(component);
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
