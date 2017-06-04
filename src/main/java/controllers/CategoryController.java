package controllers;

import org.springframework.stereotype.Controller;

import daos.core.CategoryComponentDao;

@Controller
public class CategoryController {

    private CategoryComponentDao categoryComponentDao;
    
    public void setCategoryComponentDao(CategoryComponentDao categoryComponentDao){
        this.categoryComponentDao = categoryComponentDao;
    }
    
    public void findAllCategories() {
  
    }

}
