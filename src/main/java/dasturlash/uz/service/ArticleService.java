package dasturlash.uz.service;

import dasturlash.uz.dto.ArticleDTO;
import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public ArticleDTO create(ArticleDTO dto, Integer moderatorId) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setSectionId(dto.getSectionId());
        entity.setImageId(dto.getImageId());

        entity.setModeratorId(moderatorId);
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);

        articleRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean update(String id, ArticleDTO dto) {
        ArticleEntity entity = getVisible(id);

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setSectionId(dto.getSectionId());
        entity.setImageId(dto.getImageId());

        articleRepository.save(entity);
        return true;
    }

    public Boolean delete(String id) {
        ArticleEntity entity = getVisible(id);
        entity.setVisible(false);
        articleRepository.save(entity);
        return true;
    }

    public Boolean changeStatus(String id, Integer publisherId, ArticleStatus status) {
        ArticleEntity entity = getVisible(id);

        if (status.equals(ArticleStatus.PUBLISHED) && entity.getPublishedDate() == null) {
            entity.setPublishedDate(LocalDateTime.now());
            entity.setPublisherId(publisherId);
        }

        entity.setStatus(status);
        articleRepository.save(entity);
        return true;
    }

    public List<ArticleDTO> getLast5BySection(Integer sectionId) {
        List<ArticleEntity> entityList = articleRepository.findTop5BySectionIdAndStatusAndVisibleTrueOrderByCreatedDateDesc(sectionId, ArticleStatus.PUBLISHED);
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ArticleDTO> getLast5ByCategory(Integer categoryId) {
        List<ArticleEntity> entityList = articleRepository.findTop5ByCategoryIdAndStatusAndVisibleTrueOrderByCreatedDateDesc(categoryId, ArticleStatus.PUBLISHED);
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ArticleDTO getById(String id) {
        ArticleEntity entity = getPublished(id);

        entity.setViewCount(entity.getViewCount() + 1);
        articleRepository.save(entity);

        return toDTO(entity);
    }

    private ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageId(entity.getImageId());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setViewCount(entity.getViewCount());
        dto.setSharedCount(entity.getSharedCount());
        return dto;
    }

    private ArticleEntity getVisible(String id) {
        return articleRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> new AppBadException("Article not found"));
    }

    private ArticleEntity getPublished(String id) {
        return articleRepository.findByIdAndStatusAndVisibleTrue(id, ArticleStatus.PUBLISHED)
                .orElseThrow(() -> new AppBadException("Article not found"));
    }
}
