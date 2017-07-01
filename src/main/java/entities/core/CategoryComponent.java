package entities.core;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class CategoryComponent {

    @Id
    @GeneratedValue
    private long id;

    private String code;

    private String name;

    private String icon;

    public CategoryComponent() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCategory() {
        return code == null;
    }

    public abstract void addComponent(CategoryComponent component);

    public abstract List<CategoryComponent> components();

    public abstract Product product();

    @Override
    public String toString() {
        return "CategoryComponent [id=" + id + ", code=" + code + ", name=" + name + ", icon=" + icon + "]";
    }

}
