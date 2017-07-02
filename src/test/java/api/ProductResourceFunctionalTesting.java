package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.TestsApiConfig;
import wrappers.PatchChangeDescriptionWrapper;
import wrappers.ProductBarcodeWrapper;
import wrappers.ProductWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class ProductResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testNonexistentProductCode() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        new RestBuilder<ProductWrapper>(restService.getUrl()).path(Uris.PRODUCTS).pathId("notCode").basicAuth(restService.loginAdmin(), "")
                .clazz(ProductWrapper.class).get().build();
    }

    @Test
    public void testGetProductByCode() {
        ProductWrapper product = new RestBuilder<ProductWrapper>(restService.getUrl()).path(Uris.PRODUCTS).pathId("8400000001111")
                .basicAuth(restService.loginAdmin(), "").clazz(ProductWrapper.class).get().build();
        assertEquals("8400000001111", product.getCode());
    }

    @Test
    public void testGetProductByCodeWithNonExistentProduct() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        ProductWrapper product = new RestBuilder<ProductWrapper>(restService.getUrl()).path(Uris.PRODUCTS).pathId("0000000000000")
                .basicAuth(restService.loginAdmin(), "").clazz(ProductWrapper.class).get().build();
        assertEquals("0000000000000", product.getCode());
    }

    @Test
    public void testSetProductAsDiscontinued() {
        PatchChangeDescriptionWrapper patchChangeDescription = new PatchChangeDescriptionWrapper();
        patchChangeDescription.setOp(PatchOperations.REPLACE);
        patchChangeDescription.setPath(Uris.PRODUCT_DISCONTINUED);
        patchChangeDescription.setValue("true");
        List<PatchChangeDescriptionWrapper> patchChangeDescriptions = new ArrayList<>();
        patchChangeDescriptions.add(patchChangeDescription);
        new RestBuilder<Object>(restService.getUrl()).path(Uris.PRODUCTS).pathId("8400000002223").basicAuth(restService.loginAdmin(), "")
                .body(patchChangeDescriptions).clazz(Object.class).patch().build();
        ProductWrapper product = new RestBuilder<ProductWrapper>(restService.getUrl()).path(Uris.PRODUCTS).pathId("8400000002223")
                .basicAuth(restService.loginAdmin(), "").clazz(ProductWrapper.class).get().build();
        assertTrue(product.isDiscontinued());
    }

    @Test
    public void testGenerateBarcodesPdf() {
        ProductBarcodeWrapper productBarcode8400000001111 = new ProductBarcodeWrapper("8400000001111");
        ProductBarcodeWrapper productBarcode8400000002223 = new ProductBarcodeWrapper("8400000002223");
        List<ProductBarcodeWrapper> productBarcodeWrappers = new ArrayList<>();
        productBarcodeWrappers.add(productBarcode8400000002223);
        productBarcodeWrappers.add(productBarcode8400000001111);
        new RestBuilder<Object>(restService.getUrl()).path(Uris.PRODUCTS + Uris.PRODUCT_BARCODES).body(productBarcodeWrappers).post().build();
    }

    @Test
    public void testGenerateBarcodesPdfWithNonExistentBarcode() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        ProductBarcodeWrapper productBarcode8400000001111 = new ProductBarcodeWrapper("8400000001111");
        ProductBarcodeWrapper productBarcode8400000002223 = new ProductBarcodeWrapper("0000000000000");
        List<ProductBarcodeWrapper> productBarcodeWrappers = new ArrayList<>();
        productBarcodeWrappers.add(productBarcode8400000002223);
        productBarcodeWrappers.add(productBarcode8400000001111);
        new RestBuilder<Object>(restService.getUrl()).path(Uris.PRODUCTS + Uris.PRODUCT_BARCODES).body(productBarcodeWrappers).post().build();
    }

 }
