package controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsControllerConfig;
import config.TestsPersistenceConfig;
import wrappers.CategoryComponentWrapper;
import wrappers.CategoryCompositeWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class CategoryControllerIT {

    @Autowired
    private CategoryController categoryController;

    @Test
    public void testFindAllCategories() {
        assertNotNull(categoryController.findAllCategories());
    }

    @Test
    public void testFindCategoryByName() {
        assertNotNull(categoryController.findCategoryComponentByName("TextilePrintings"));
    }

    @Test
    public void testFindCategoryByNameWithNonExistentName() {
        assertNull(categoryController.findCategoryComponentByName("non_existent"));
    }

    @Test
    public void testFindCategoryWrapperByName() {
        CategoryComponentWrapper componentWrapper = categoryController.findCategoryComponentWrapperByName("TextilePrintings");
        assertNotNull(componentWrapper);
        assertNull(componentWrapper.getCode());
        assertNull(componentWrapper.getImage());
        assertNotNull(componentWrapper.getName());
        assertFalse(((CategoryCompositeWrapper) componentWrapper).getComponents().isEmpty());
    }
}
