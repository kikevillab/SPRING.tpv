package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.CategoryComponentNotFound;
import config.ResourceNames;
import controllers.CategoryController;
import entities.core.CategoryComponent;

@RestController
@RequestMapping(Uris.VERSION + Uris.CATEGORIES)
public class CategoryResource {
    private CategoryController categoryController;

    @Autowired
    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }

    @RequestMapping(method = RequestMethod.GET)
    public CategoryComponent findAllCategories() throws CategoryComponentNotFound {
        throwExceptionIfCategoryComponentDoesNotExist(ResourceNames.CATEGORIES_ROOT);
        return categoryController.findAllCategories();
    }

    @RequestMapping(value = Uris.CATEGORY_NAME, method = RequestMethod.GET)
    public CategoryComponent findCategoryByName(@PathVariable String name) throws CategoryComponentNotFound {
        throwExceptionIfCategoryComponentDoesNotExist(name);
        return categoryController.findCategoryComponentByName(name);
    }

    private void throwExceptionIfCategoryComponentDoesNotExist(String name) throws CategoryComponentNotFound {
        if (!categoryController.existsCategory(name)) {
            throw new CategoryComponentNotFound("CategoryComponent: " + name);
        }
    }
}
