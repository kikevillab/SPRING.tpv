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
        this(null,"");
    }

    public CategoryComposite(String code, String name) {
        super(code, name);
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

    @Override
    public Product product() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return super.toString() + "CategoryComposite [categoryComponents=" + categoryComponents + "]";
    }
}
