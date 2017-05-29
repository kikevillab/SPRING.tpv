package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.NotFoundProductCodeException;
import controllers.ProductController;
import entities.core.Product;
import wrappers.PatchChangeDescriptionWrapper;
import wrappers.ProductBarcodeWrapper;
import wrappers.ProductWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.PRODUCTS)
public class ProductResource {

    private ProductController productController;

    @Autowired
    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

    // @PreAuthorize("hasRole('ADMIN')or hasRole('MANAGER') or hasRole('OPERATOR')")
    @RequestMapping(value = Uris.CODE, method = RequestMethod.GET)
    public ProductWrapper getProductByCode(@PathVariable(value = "code") String code) throws NotFoundProductCodeException {
        throwExceptionIfProductDoesNotExists(code);
        return new ProductWrapper(productController.getProductByCode(code));
    }

    @RequestMapping(value = Uris.CODE, method = RequestMethod.PATCH)
    public void setProductAsDiscontinued(@PathVariable String code, @RequestBody List<PatchChangeDescriptionWrapper> patchChangeDescriptionsWrapper) throws NotFoundProductCodeException{
        throwExceptionIfProductDoesNotExists(code);
        for(PatchChangeDescriptionWrapper patchRequestBodyWrapper : patchChangeDescriptionsWrapper){
            String operation = patchRequestBodyWrapper.getOp();
            String path = patchRequestBodyWrapper.getPath();
            String value = patchRequestBodyWrapper.getValue();
            if(operation.equals(PatchOperations.REPLACE)){
                if(path.equals(Uris.DISCONTINUED)){
                    productController.setProductAsDiscontinued(code, Boolean.parseBoolean(value));
                }
            }
        }
    }
    
    @RequestMapping(value = Uris.BARCODES, method = RequestMethod.POST)
    public byte[] generateBarcodesPdf(@RequestBody List<ProductBarcodeWrapper> productBarcodeWrappers) throws NotFoundProductCodeException{
        for(ProductBarcodeWrapper productBarcodeWrapper : productBarcodeWrappers){
            throwExceptionIfProductDoesNotExists(productBarcodeWrapper.getBarcode());
        }     
        return productController.generateBarcodesPdf(productBarcodeWrappers);
    }
    
    private void throwExceptionIfProductDoesNotExists(String code) throws NotFoundProductCodeException{
        Product product = productController.getProductByCode(code);
        if (product == null) {
            throw new NotFoundProductCodeException("Product code: " + code);
        }
    }
}
