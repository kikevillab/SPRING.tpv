package api;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import entities.core.CategoryComponent;

public class CategoryResourceFunctionalTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpOnce() {
        new RestService().seedDatabase();
    }

    @AfterClass
    public static void tearDownOnce() {
        new RestService().deleteAllExceptAdmin();
    }
 
    @Test
    public void testFindCategoryByNameWithNonExistentName(){
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        new RestBuilder<CategoryComponent>(RestService.URL).path(Uris.CATEGORIES).pathId("non_existent")
                .clazz(CategoryComponent.class).get().build();
    }
}
