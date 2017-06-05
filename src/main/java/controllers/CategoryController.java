package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import config.ResourceNames;
import daos.core.CategoryComponentDao;
import entities.core.CategoryComponent;

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
    
    public CategoryComponent findCategoryComponentByName(String name){
        return categoryComponentDao.findByName(name);
    }

    public boolean existsCategory(String name){
        return findCategoryComponentByName(name) != null;
    }

}
