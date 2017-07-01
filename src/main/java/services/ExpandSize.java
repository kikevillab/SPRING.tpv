package services;

import java.util.ArrayList;
import java.util.List;

import entities.core.Article;

public class ExpandSize {

    public boolean isArticleExpandable(Article article) {
        return article.getReference().indexOf("[") != -1;
    }

    public List<Article> expandArticle(Article article) {
        List<Article> articlesExpanded = new ArrayList<>();
        Barcode barcode = new Barcode();
        if (this.isArticleExpandable(article)) {
            String articleReferenceBase = article.getReference().substring(0, article.getReference().indexOf("["));
            int from = Integer.parseInt(
                    article.getReference().substring(article.getReference().indexOf("[") + 1, article.getReference().indexOf("..")));
            int to = Integer.parseInt(
                    article.getReference().substring(article.getReference().indexOf("..") + 2, article.getReference().indexOf("]")));
            for (int i = from; i <= to; i += 2) {
                Article articleExpanded = new Article();
                articleExpanded.setRetailPrice(article.getRetailPrice());
                articleExpanded.setWholesalePrice(article.getWholesalePrice());
                articleExpanded.setStock(article.getStock());
                articleExpanded.setProvider(article.getProvider());
                articleExpanded.setImage(article.getImage());
                String base = article.getCode();
                if (i < 10) {
                    base += "0";
                }
                articleExpanded.setCode(base + (i) + "000" + barcode.ean13ControlCodeCalculator(base + "000"));
                articleExpanded.setReference(articleReferenceBase + i);
                articleExpanded.setDescription(article.getDescription() + i);
                articlesExpanded.add(articleExpanded);
            }
        } else {
            articlesExpanded.add(article);
        }
        return articlesExpanded;
    }

}
