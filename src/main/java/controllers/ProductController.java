package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.ProductDao;
import entities.core.Product;
import services.PdfGenerationService;
import wrappers.ProductBarcodeWrapper;

@Controller
public class ProductController {

    private ProductDao productDao;

    private PdfGenerationService pdfGenService;

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    public void setPdfGenerationService(PdfGenerationService pdfGenService) {
        this.pdfGenService = pdfGenService;
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

    public byte[] generateBarcodesPdf(List<ProductBarcodeWrapper> productBarcodeWrappers) {
        List<String> productBarcodes = productBarcodeWrappers.stream().map(ProductBarcodeWrapper::getBarcode)
                .filter(barcode -> barcode != null && !barcode.isEmpty()).collect(Collectors.toList());
        return pdfGenService.generateBarcodesPdf(productBarcodes);
    }
}
