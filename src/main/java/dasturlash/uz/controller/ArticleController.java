package dasturlash.uz.controller;

import dasturlash.uz.dto.ArticleDTO;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/moderator/create")
    public ResponseEntity<ArticleDTO> create(@RequestHeader("ProfileId") Integer moderatorId,
                                             @Valid @RequestBody ArticleDTO dto) {
        return ResponseEntity.ok(articleService.create(dto, moderatorId));
    }

    @PutMapping("/moderator/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") String id,
                                          @Valid @RequestBody ArticleDTO dto) {
        return ResponseEntity.ok(articleService.update(id, dto));
    }

    @DeleteMapping("/moderator/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PostMapping("/publisher/publish/{id}")
    public ResponseEntity<Boolean> publish(@RequestHeader("ProfileId") Integer publisherId,
                                           @PathVariable("id") String id) {
        return ResponseEntity.ok(articleService.changeStatus(id, publisherId, ArticleStatus.PUBLISHED));
    }

    @GetMapping("/public/last5-by-section/{id}")
    public ResponseEntity<List<ArticleDTO>> getLast5BySection(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleService.getLast5BySection(id));
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<ArticleDTO> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(articleService.getById(id));
    }
}
