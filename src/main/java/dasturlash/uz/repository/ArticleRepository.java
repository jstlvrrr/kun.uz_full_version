package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.enums.ArticleStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
    List<ArticleEntity> findAllByVisibleTrueAndStatusOrderByCreatedDateDesc(ArticleStatus status);
    List<ArticleEntity> findTop5BySectionIdAndStatusAndVisibleTrueOrderByCreatedDateDesc(Integer sectionId, ArticleStatus status);

    List<ArticleEntity> findTop5ByCategoryIdAndStatusAndVisibleTrueOrderByCreatedDateDesc(Integer categoryId, ArticleStatus status);

    Optional<ArticleEntity> findByIdAndVisibleTrue(String id);

    Optional<ArticleEntity> findByIdAndStatusAndVisibleTrue(String id, ArticleStatus status);
}
