package funnyboard.controller;

import funnyboard.dto.ArticleForm;
import funnyboard.dto.ArticleUpdateRequest;
import funnyboard.service.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Tag(name = "ArticleController", description = "게시물 CRUD API")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/api/articles")
    public List<ArticleForm> findAllArticles() {
        return articleService.findAllArticles()
                .stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleForm> findArticleById(@PathVariable("id") Long id) {
        ArticleForm articleForm = articleService.findArticleById(id);
        return (articleForm != null) ?
                ResponseEntity.status(HttpStatus.OK).body(articleForm) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/articles")
    public ResponseEntity<ArticleForm> create(@RequestBody ArticleForm dto) {
        ArticleForm articleForm = articleService.create(dto);
        return (articleForm != null) ?
                ResponseEntity.status(HttpStatus.OK).body(articleForm) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<ArticleForm> update(@PathVariable("id") Long id, @RequestBody ArticleUpdateRequest dto) {
        ArticleForm articleForm = articleService.update(id, dto);
        return (articleForm != null) ?
                ResponseEntity.status(HttpStatus.OK).body(articleForm) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<ArticleForm> delete(@PathVariable("id") Long id) {
        ArticleForm articleForm = articleService.delete(id);
        return (articleForm != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
