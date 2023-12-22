package funnyboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import funnyboard.dto.ArticleForm;
import funnyboard.entity.Article;
import funnyboard.repository.ArticleRepository;
import funnyboard.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void beforeEach() {
    }

    @Test
    void 모든_게시글_조회하기() throws JsonProcessingException {
        //given

        //when
        List<Article> expected = articleRepository.findAll();
        List<Article> articles = articleService.findAllArticles();

        //then
        assertThat(articles.toString()).isEqualTo(expected.toString());
    }

    @Test
    void 특정_게시글_조회하기() {
        Article expected = articleRepository.findById(2l).orElse(null);
        Article article = articleService.findArticleById(2l);

        //then
        assertThat(article.toString()).isEqualTo(expected.toString());
    }

    @Test
    void 게시글_생성_테스트() {
        //given
        ArticleForm dto = new ArticleForm(null, "hello juny", "im tony");
        Article acturl = articleService.create(dto);
        Article expected = articleRepository.findById(acturl.getId()).orElse(null);

        //then
        assertThat(acturl.toString()).isEqualTo(expected.toString());
    }

    @Test
    void 게시글_업데이트_테스트() {
        //when
        ArticleForm dto = new ArticleForm(3l, "hello chicken", "chicken day");
        Article updated = articleService.update(3l, dto);
        Article expected = dto.toEntity();

        assertThat(updated.toString()).isEqualTo(expected.toString());
    }

    @Test
    void 제목만_업데이트_테스트() {
        //given

        //when
        ArticleForm dto = new ArticleForm(3l, "hello chicken", null);
        Article updated = articleService.update(3l, dto);
        Article origin = articleRepository.findById(3l).orElse(null);
        Article expected = new Article(3l, "hello chicken", origin.getContent());
        System.out.println("updated = " + updated);

        assertThat(updated.toString()).isEqualTo(expected.toString());
    }

    @Test
    void 게시글_삭제() {
        //given

        //when
        Article deleted = articleService.delete(3l);
        Article expected = articleRepository.findById(3l).orElse(null);

        //then
        assertThat(expected).isNull();
    }
}
