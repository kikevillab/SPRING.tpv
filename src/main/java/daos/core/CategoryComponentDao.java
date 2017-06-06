package daos.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import entities.core.CategoryComponent;

public interface CategoryComponentDao extends JpaRepository<CategoryComponent, Long> {
    CategoryComponent findByName(String name);

    Page<CategoryComponent> findByName(Pageable pageable, String name);

    @Query(
            value = "SELECT cc.categoryComponents FROM CategoryComponent cc WHERE cc.name LIKE %:name%", 
            countQuery = "SELECT count(elements(cc.categoryComponents)) from CategoryComponent cc WHERE cc.name LIKE %:name%"
            )
    Page<CategoryComponent> findChildrenByParentName(Pageable pageable, @Param("name") String name);

    @Query(
            value = "SELECT cc.categoryComponents FROM CategoryComponent cc WHERE cc.id = :id", 
            countQuery = "SELECT count(elements(cc.categoryComponents)) from CategoryComponent cc WHERE cc.id = :id"
            )
    Page<CategoryComponent> findChildrenByParentId(Pageable pageable, @Param("id") long id);
}
