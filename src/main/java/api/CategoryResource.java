package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.CategoryComponentNotFoundException;
import config.ResourceNames;
import controllers.CategoryController;
import entities.core.CategoryComponent;
import wrappers.CategoryComponentWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.CATEGORIES)
public class CategoryResource {
    private CategoryController categoryController;

    @Autowired
    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }

    @RequestMapping(value = Uris.SEARCH, method = RequestMethod.GET)
    public Page<CategoryComponentWrapper> findCategoriesPaginatedByDepthLevel(Pageable pageable, @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name) throws CategoryComponentNotFoundException {
        Page<CategoryComponentWrapper> categoryComponentWrapperPage;
        if (id != null) {
            categoryComponentWrapperPage = categoryController.findCategoryChildrenPaginatedByParentId(pageable, id.longValue());
        } else {
            if (name == null || name.isEmpty()) {
                name = ResourceNames.CATEGORIES_ROOT;
                categoryComponentWrapperPage = categoryController.findCategoriesChildrenPaginatedByParentName(pageable, name);
            } else {
                categoryComponentWrapperPage = categoryController.findCategoriesPaginatedByNameContaining(pageable, name);
            }           
        }
        return categoryComponentWrapperPage;
    }

    @RequestMapping(method = RequestMethod.GET)
    public CategoryComponent findAllCategories() throws CategoryComponentNotFoundException {
        throwExceptionIfCategoryComponentDoesNotExist(ResourceNames.CATEGORIES_ROOT);
        return categoryController.findAllCategories();
    }

    @RequestMapping(value = Uris.CATEGORY_NAME, method = RequestMethod.GET)
    public CategoryComponent findCategoryByName(@PathVariable String name) throws CategoryComponentNotFoundException {
        throwExceptionIfCategoryComponentDoesNotExist(name);
        return categoryController.findCategoryComponentByName(name);
    }

    private void throwExceptionIfCategoryComponentDoesNotExist(String name) throws CategoryComponentNotFoundException {
        if (!categoryController.existsCategory(name)) {
            throw new CategoryComponentNotFoundException("CategoryComponent: " + name);
        }
    }
}
