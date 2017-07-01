package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.CategoryProduct;

public interface ProductCategoryDao extends JpaRepository<CategoryProduct, Long> {

}
