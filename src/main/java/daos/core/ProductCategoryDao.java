package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.ProductCategory;

public interface ProductCategoryDao extends JpaRepository<ProductCategory, Long> {

}
