package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import controllers.CategoryController;

@RestController
@RequestMapping(Uris.VERSION + Uris.CATEGORIES)
public class CategoryResource {
    private CategoryController categoryController;
    
    @Autowired
    public void setCategoryController(CategoryController categoryController){
        this.categoryController = categoryController;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public void findAllCategories(){
        categoryController.findAllCategories();
    }
    
}
