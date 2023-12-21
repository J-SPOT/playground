package funnyboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import funnyboard.entity.Article;
import funnyboard.repository.ArticleRepository;
import funnyboard.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @MockBean
    private ArticleRepository articleRepository;

    @BeforeEach
    void beforeEach() {
        List<Article> mockArticles = Arrays.asList(
                new Article(1l, "juny", "주니왔다"),
                new Article(2l, "jiny", "지니왔다"),
                new Article(3l, "반가워1", "애드라")
        );
        when(articleRepository.findAll()).thenReturn(mockArticles);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(new Article(1l, "juny", "주니왔다")));
        when(articleRepository.findById(2L)).thenReturn(Optional.of(new Article(2l, "jiny", "지니왔다")));

    }

    @Test
    void 모든_게시글_조회하기() throws JsonProcessingException {
        //given

        //when
        List<Article> expected = articleRepository.findAll();
        List<Article> articles = articleService.findAllArticles();

        //then
        assertThat(articles).isEqualTo(expected);
    }
}
