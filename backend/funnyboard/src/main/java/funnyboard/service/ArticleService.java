package funnyboard.service;

import funnyboard.dto.ArticleForm;
import funnyboard.domain.Article;
import funnyboard.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article create(ArticleForm dto) {
        // 제대로된 정보가 입력되었는지 validation check
        try {
            if (dto.getId() != null) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            log.error("create, dto get id:{}", dto.getId());
            return null;
        }
        Article savedArticle = articleRepository.save(dto.toEntity());
        return savedArticle;
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    public Article findArticleById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        try {
            if (article == null) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            log.error("findArticleById, articles are {}", article);
        }
        return article;
    }

    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        if (id != dto.getId()) {
            log.error("update 요청 id:{}와 게시글 생성 id:{}와 같지 않습니다.", id, dto.getId());
            return null;
        }
        Article origin = articleRepository.findById(id).orElse(null);
        try {
            if (origin == null) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            log.error("update, 기존 내용이 없습니다. {}", e.getMessage());
            return null;
        }
        origin.patch(dto);
        Article savedArticle = articleRepository.save(origin);
        return savedArticle;
    }

    public Article delete(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        try {
            if (article == null) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            log.error("delete, 존재하지 않는 게시글입니다.");
            return null;
        }
        articleRepository.delete(article);
        return article;
    }
}
