package funnyboard.integration;

import funnyboard.domain.Article;
import funnyboard.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ArticleRepositoryIntegrationTest {

    @Autowired
    private ArticleRepository articleRepository;
    Article article1, article2;

    @BeforeEach
    void setup() {
        article1 = new Article(1L, "제목 1", "내용 1");
        article2 = new Article(2L, "제목 2", "내용 2");
    }


    @DisplayName("게시글 저장한다")
    @Test
    void SaveArticle_ReturnSavedArticle() {
        //given

        //when
        Article savedArticle = articleRepository.save(article1);

        //then
        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getId()).isEqualTo(savedArticle.getId());
        assertThat(savedArticle.getTitle()).isEqualTo(article1.getTitle());
        assertThat(savedArticle.getContent()).isEqualTo(article1.getContent());
    }

    @DisplayName("게시글 id로 게시글을 찾는다")
    @Test
    void FindById_ReturnSavedArticle() {
        //given
        Article savedArticle = articleRepository.save(article1);
        //when
        Article findArticle = articleRepository.findById(savedArticle.getId()).orElse(null);

        //then
        assertThat(findArticle).isNotNull();
        assertThat(savedArticle.getId()).isEqualTo(savedArticle.getId());
        assertThat(savedArticle.getTitle()).isEqualTo(savedArticle.getTitle());
        assertThat(savedArticle.getContent()).isEqualTo(savedArticle.getContent());
    }

    @DisplayName("저장된 모든 게시글을 찾는다")
    @Test
    void FindAll_ReturnSavedArticles() {
        //given
        Article savedArticle1 = articleRepository.save(article1);
        Article savedArticle2 = articleRepository.save(article2);
        //when
        List<Article> result = articleRepository.findAll();

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }
}
