package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.NotFoundProductCodeException;
import controllers.ProductController;
import entities.core.Product;
import wrappers.PatchChangeDescriptionWrapper;
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
        Product product = productController.getProductByCode(code);
        if (product == null) {
            throw new NotFoundProductCodeException("Product code: " + code);
        }
        return new ProductWrapper(product);
    }

    @RequestMapping(value = Uris.ID, method = RequestMethod.PATCH)
    public void setProductAsDiscontinued(@PathVariable long id, @RequestBody List<PatchChangeDescriptionWrapper> patchChangeDescriptionsWrapper){
        for(PatchChangeDescriptionWrapper patchRequestBodyWrapper : patchChangeDescriptionsWrapper){
            String operation = patchRequestBodyWrapper.getOp();
            String path = patchRequestBodyWrapper.getPath();
            String value = patchRequestBodyWrapper.getValue();
            if(operation.equals(PatchOperations.REPLACE)){
                if(path.equals(Uris.DISCONTINUED)){
                    productController.setProductAsDiscontinued(id, Boolean.parseBoolean(value));
                }
            }
        }
    }
}
