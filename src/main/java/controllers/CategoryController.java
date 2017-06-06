package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import config.ResourceNames;
import daos.core.CategoryComponentDao;
import entities.core.CategoryComponent;
import wrappers.CategoryComponentWrapper;
import wrappers.CategoryCompositeWrapper;
import wrappers.ProductCategoryWrapper;

@Controller
public class CategoryController {

    private CategoryComponentDao categoryComponentDao;

    @Autowired
    public void setCategoryComponentDao(CategoryComponentDao categoryComponentDao) {
        this.categoryComponentDao = categoryComponentDao;
    }

    public CategoryComponent findAllCategories() {
        CategoryComponent categoryComponent = findCategoryComponentByName(ResourceNames.CATEGORIES_ROOT);
        return categoryComponent;
    }

    public CategoryComponent findCategoryComponentByName(String name) {
        return categoryComponentDao.findByName(name);
    }

    public CategoryComponentWrapper findCategoryComponentWrapperByName(String name) {
        CategoryComponent categoryComponent = findCategoryComponentByName(name);
        return getCategoryComponentWrapper(categoryComponent);
    }

    public Page<CategoryComponentWrapper> findCategoriesPaginatedByName(Pageable pageable, String name) {
        Page<CategoryComponent> categoryComponentPage = categoryComponentDao.findByName(pageable, name);
        List<CategoryComponentWrapper> componentWrappers = new ArrayList<>();
        for(CategoryComponent component : categoryComponentPage.getContent()){
                componentWrappers.add(getCategoryComponentWrapper(component));        
        }
        return new PageImpl<CategoryComponentWrapper>(componentWrappers, pageable,
                categoryComponentPage.getTotalElements());
    }

    private CategoryComponentWrapper getCategoryComponentWrapper(CategoryComponent categoryComponent) {
        if (categoryComponent.isCategory()) {
            return new CategoryCompositeWrapper(categoryComponent);
        } else {
            return new ProductCategoryWrapper(categoryComponent);
        }
    }

    public boolean existsCategory(String name) {
        return findCategoryComponentByName(name) != null;
    }

}
