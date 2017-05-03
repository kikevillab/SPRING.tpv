package api;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import wrappers.ProductWrapper;

public class ProductResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpOnce() {
        new RestService().seedDatabase();
    }

    @Test
    public void testNonexistentProductCode() {
        String productCode = "justTesting-123";
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String token = new RestService().loginAdmin();
        new RestBuilder<ProductWrapper>(RestService.URL).path(Uris.PRODUCTS).pathId(productCode).basicAuth(token, "")
                .clazz(ProductWrapper.class).get().build();
    }

    @Test
    public void testGetProductByCode() {
        String productCode = "article0";
        String token = new RestService().loginAdmin();
        ProductWrapper product = new RestBuilder<ProductWrapper>(RestService.URL).path(Uris.PRODUCTS).pathId(productCode)
                .basicAuth(token, "").clazz(ProductWrapper.class).get().build();
        assertEquals(productCode, product.getCode());
    }

    // Waiting for front-end to implement login and add roles.
    // @Test
    // public void testGetProductByCodeUnauthorized() {
    // String productCode = "article0";
    // thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
    // new RestBuilder<ProductWrapper>(RestService.URL).path(Uris.PRODUCTS).pathId(productCode).clazz(ProductWrapper.class).get().build();
    // }

    @AfterClass
    public static void tearDownOnce() {
        new RestService().deleteAll();
    }
}
