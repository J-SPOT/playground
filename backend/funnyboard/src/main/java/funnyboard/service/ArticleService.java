package funnyboard.service;

import funnyboard.config.error.exception.article.ArticleCreationHasId;
import funnyboard.config.error.exception.article.ArticleNotFoundException;
import funnyboard.config.error.exception.article.ArticleUpdateIdNotValid;
import funnyboard.domain.Article;
import funnyboard.dto.ArticleForm;
import funnyboard.dto.ArticleUpdateRequest;
import funnyboard.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleForm create(ArticleForm dto) {
        if (dto.getId() != null) {
           throw new ArticleCreationHasId();
        }
        Article savedArticle = articleRepository.save(dto.toEntity());
        return Article.toForm(savedArticle);
    }

    public List<ArticleForm> findAllArticles() {
        return articleRepository.findAll()
                .stream()
                .map(Article::toForm)
                .collect(Collectors.toList());
    }

    public ArticleForm findArticleById(Long id) {
        return articleRepository.findById(id)
                .map(Article::toForm)
                .orElseThrow(() -> new ArticleNotFoundException());
    }

    public ArticleForm update(Long id, ArticleUpdateRequest dto) {
        Article article = dto.toEntity();

        if (id != dto.getId()) {
            throw new ArticleUpdateIdNotValid();
        }
        Article origin = articleRepository.findById(id).orElseThrow(
                () -> new ArticleNotFoundException());
        origin.patch(dto);
        Article savedArticle = articleRepository.save(origin);
        return Article.toForm(savedArticle);
    }

    public ArticleForm delete(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new ArticleNotFoundException());
        articleRepository.delete(article);
        return Article.toForm(article);
    }
}
