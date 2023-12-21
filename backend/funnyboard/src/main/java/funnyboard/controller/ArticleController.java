package funnyboard.controller;

import funnyboard.entity.Article;
import funnyboard.repository.ArticleRepository;
import funnyboard.service.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "ArticleController", description = "게시물 CRUD API")
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    public ArticleController(ArticleRepository articleRepository, ArticleService articleService) {
        this.articleRepository = articleRepository;
        this.articleService = articleService;
    }

    @GetMapping("/api/articles")
    public List<Article> findAllArticles() {
        return articleService.findAllArticles();
    }
}
