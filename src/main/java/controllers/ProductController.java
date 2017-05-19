package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.ProductDao;
import entities.core.Product;

@Controller
public class ProductController {

    private ProductDao productDao;

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product getProductByCode(String code) {
        return productDao.findOne(code);
    }

    public boolean productCodeExists(String productCode) {
        Product product = productDao.findOne(productCode);
        return product != null;
    }

    public void setProductAsDiscontinued(String code, boolean discontinued) {
        Product product = productDao.findOne(code);
        product.setDiscontinued(discontinued);
        productDao.saveAndFlush(product);
    }
}
