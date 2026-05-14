package dasturlash.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private String id;

    @NotBlank(message = "Title required")
    private String title;

    @NotBlank(message = "Description required")
    private String description;

    @NotBlank(message = "Content required")
    private String content;

    private String imageId;

    @NotNull(message = "RegionId required")
    @Min(value = 1, message = "RegionId have to higher than 0")
    private Integer regionId;

    @NotNull(message = "CategoryId required")
    @Min(value = 1, message = "CategoryId have to higher than 0")
    private Integer categoryId;

    @NotNull(message = "SectionId required")
    @Min(value = 1, message = "SectionId have to higher than 0")
    private Integer sectionId;

    private LocalDateTime publishedDate;
    private Integer viewCount;
    private Integer sharedCount;
}
