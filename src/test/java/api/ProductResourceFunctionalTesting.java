package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import wrappers.PatchChangeDescriptionWrapper;
import wrappers.ProductBarcodeWrapper;
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
        String productCode = "8400000001111";
        String token = new RestService().loginAdmin();
        ProductWrapper product = new RestBuilder<ProductWrapper>(RestService.URL).path(Uris.PRODUCTS).pathId(productCode)
                .basicAuth(token, "").clazz(ProductWrapper.class).get().build();
        assertEquals(productCode, product.getCode());
    }
    
    @Test
    public void testGetProductByCodeWithNonExistentProduct(){
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String productCode = "0000000000000";
        String token = new RestService().loginAdmin();
        ProductWrapper product = new RestBuilder<ProductWrapper>(RestService.URL).path(Uris.PRODUCTS).pathId(productCode)
                .basicAuth(token, "").clazz(ProductWrapper.class).get().build();
        assertEquals(productCode, product.getCode());
    }
    
    @Test
    public void testSetProductAsDiscontinued(){
        String productCode = "8400000002223";
        String token = new RestService().loginAdmin();
        PatchChangeDescriptionWrapper patchChangeDescription = new PatchChangeDescriptionWrapper();
        patchChangeDescription.setOp(PatchOperations.REPLACE);
        patchChangeDescription.setPath(Uris.DISCONTINUED);
        patchChangeDescription.setValue("true");
        List<PatchChangeDescriptionWrapper> patchChangeDescriptions = new ArrayList<>();
        patchChangeDescriptions.add(patchChangeDescription);
        new RestBuilder<Object>(RestService.URL).path(Uris.PRODUCTS).pathId(productCode)
        .basicAuth(token, "").body(patchChangeDescriptions).clazz(Object.class).patch().build();
        ProductWrapper product = new RestBuilder<ProductWrapper>(RestService.URL).path(Uris.PRODUCTS).pathId(productCode)
                .basicAuth(token, "").clazz(ProductWrapper.class).get().build();
        assertTrue(product.isDiscontinued());
    }
    
    @Test
    public void testGenerateBarcodesPdf(){
        ProductBarcodeWrapper productBarcode8400000001111 = new ProductBarcodeWrapper("8400000001111");
        ProductBarcodeWrapper productBarcode8400000002223 = new ProductBarcodeWrapper("8400000002223");
        List<ProductBarcodeWrapper> productBarcodeWrappers = new ArrayList<>();
        productBarcodeWrappers.add(productBarcode8400000002223);
        productBarcodeWrappers.add(productBarcode8400000001111);
        new RestBuilder<Object>(RestService.URL).path(Uris.PRODUCTS + Uris.BARCODES).body(productBarcodeWrappers).post().build();
    }
    
    @Test
    public void testGenerateBarcodesPdfWithNonExistentBarcode(){
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        ProductBarcodeWrapper productBarcode8400000001111 = new ProductBarcodeWrapper("8400000001111");
        ProductBarcodeWrapper productBarcode8400000002223 = new ProductBarcodeWrapper("0000000000000");
        List<ProductBarcodeWrapper> productBarcodeWrappers = new ArrayList<>();
        productBarcodeWrappers.add(productBarcode8400000002223);
        productBarcodeWrappers.add(productBarcode8400000001111);
        new RestBuilder<Object>(RestService.URL).path(Uris.PRODUCTS + Uris.BARCODES).body(productBarcodeWrappers).post().build();
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
