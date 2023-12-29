package funnyboard.service;

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
            throw new IllegalArgumentException("create, dto get id:" + dto.getId());
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
                .orElseThrow(() -> new IllegalArgumentException("findArticleById, article not found id: " + id));
    }

    public ArticleForm update(Long id, ArticleUpdateRequest dto) {
        Article article = dto.toEntity();
        if (id != dto.getId()) {
            throw new IllegalArgumentException("update, 요청 id: " + id + "게시글 생성 id: " + dto.getId() + "와 같지 않습니다.");
        }
        Article origin = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("update, 존재하지 않는 게시글 입니다."));
        origin.patch(dto);
        Article savedArticle = articleRepository.save(origin);
        return Article.toForm(savedArticle);
    }

    public ArticleForm delete(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("delete, 존재하지 않는 게시글 입니다."));
        articleRepository.delete(article);
        return Article.toForm(article);
    }
}
