package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.ProductDao;
import entities.core.Product;
import wrappers.ProductWrapper;

@Controller
public class ProductController {

    private ProductDao productDao;

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductWrapper getProductByCode(String code) {
        ProductWrapper productWrapper = null;
        Product product = productDao.findFirstByCode(code);
        if (product != null) {
            productWrapper = new ProductWrapper(product);
        }
        return productWrapper;
    }
}
