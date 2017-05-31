package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.CategoryComposite;

public interface CategoryCompositeDao extends JpaRepository<CategoryComposite, Long>{

}
