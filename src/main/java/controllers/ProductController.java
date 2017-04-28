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
        return productDao.findFirstByCode(code);
    }
}
