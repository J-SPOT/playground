package funnyboard;

import funnyboard.domain.Article;
import funnyboard.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ArticleRepositoryTest {

    @Mock
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
        Mockito.when(articleRepository.save(article1)).thenReturn(article1);

        //when
        Article savedArticle = articleRepository.save(article1);

        //then
        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getId()).isEqualTo(1L);
        assertThat(savedArticle.getTitle()).isEqualTo(article1.getTitle());
        assertThat(savedArticle.getContent()).isEqualTo(article1.getContent());
    }

    @DisplayName("게시글 id로 게시글을 찾는다")
    @Test
    void FindById_ReturnSavedArticle() {
        //given
        Mockito.when(articleRepository.findById(1L))
                .thenReturn(Optional.ofNullable(article1));
        Article savedArticle = articleRepository.save(article1);
        //when
        Article findArticle = articleRepository.findById(1L).orElse(null);

        //then
        assertThat(findArticle).isEqualTo(article1);
    }

    @DisplayName("저장된 모든 게시글을 찾는다")
    @Test
    void FindAll_ReturnSavedArticles() {
        //given
        Mockito.when(articleRepository.findAll())
                .thenReturn(Arrays.asList(article1, article2));

        Article savedArticle1 = articleRepository.save(article1);
        Article savedArticle2 = articleRepository.save(article1);
        //when
        List<Article> result = articleRepository.findAll();

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }
}
