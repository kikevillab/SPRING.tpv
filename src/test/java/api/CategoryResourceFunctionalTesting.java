package api;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.TestsApiConfig;
import entities.core.CategoryComponent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class CategoryResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFindCategoryByNameWithNonExistentName() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        new RestBuilder<CategoryComponent>(restService.getUrl()).path(Uris.CATEGORIES).pathId("non_existent")
                .basicAuth(restService.loginAdmin(), "").clazz(CategoryComponent.class).get().build();
    }
}
