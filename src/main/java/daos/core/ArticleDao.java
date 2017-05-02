package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.Article;

public interface ArticleDao extends JpaRepository<Article, Long> {

}
