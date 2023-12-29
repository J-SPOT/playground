package funnyboard;

import funnyboard.domain.Article;
import funnyboard.dto.ArticleForm;
import funnyboard.dto.ArticleUpdateRequest;
import funnyboard.repository.ArticleRepository;
import funnyboard.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    Article article1, article2;
    ArticleForm articleFormRequest,
            articleForm1,
            articleForm2;
    ArticleUpdateRequest articleUpdateRequest1, articleUpdateRequest2;

    @BeforeEach
    void setup() {
        articleFormRequest = new ArticleForm(null, "제목 1", "내용 1");

        articleUpdateRequest1 = new ArticleUpdateRequest(1L, "제목 수정 1", "내용 수정 1");
        articleUpdateRequest2 = new ArticleUpdateRequest(1L, "제목 수정 1", null);

        articleForm1 = new ArticleForm(1L, "제목 1", "내용 1");
        articleForm2 = new ArticleForm(2L, "제목 2", "내용 2");

        article1 = new Article(1L, "제목 1", "내용 1");
        article2 = new Article(2L, "제목 2", "내용 2");
    }

    @DisplayName("모든 게시글 조회하기")
    @Test
    void FindArticles_ReturnFindArticles() {
        //given
        List<Article> articles = Arrays.asList(article1, article2);
        List<ArticleForm> articlesForm = articles.stream().map(Article::toForm).collect(Collectors.toList());
        Mockito.when(articleRepository.findAll()).thenReturn(articles);

        //when
        List<ArticleForm> allArticlesForm = articleService.findAllArticles();

        //then
        assertThat(allArticlesForm).isNotNull();
        assertThat(allArticlesForm).usingRecursiveComparison().isEqualTo(articlesForm);
    }
    
    @DisplayName("특정 게시글 조회하기")
    @Test
    void FindArticle_ReturnFindArticle() {
        //given
        Article article = article1;
        ArticleForm articleForm = Article.toForm(article);
        Mockito.when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));

        //when
        ArticleForm findArticleForm = articleService.findArticleById(article1.getId());

        //then
        assertThat(findArticleForm).isNotNull();
        assertThat(findArticleForm).usingRecursiveComparison().isEqualTo(articleForm);
    }
    
    @DisplayName("특정 게시글 생성하기")
    @Test
    void CreateArticle_ReturnCreatedArticle() {
        //given
        ArticleForm articleForm = articleFormRequest;
        Article article = article1;

        Mockito.when(articleRepository.save(Mockito.any(Article.class))).thenReturn(article);


        //when
        ArticleForm createdArticleForm = articleService.create(articleForm);

        //then
        assertThat(createdArticleForm).isNotNull();
        assertThat(createdArticleForm).usingRecursiveComparison().isEqualTo(Article.toForm(article));
    }

    @DisplayName("특정 게시글 제목과 내용 변경하기")
    @Test
    void UpdateArticleAll_ReturnUpdatedArticle() {
        //given
        Article mockArticle = article1;
        Mockito.when(articleRepository.findById(mockArticle.getId())).thenReturn(Optional.of(article1));
        Mockito.when(articleRepository.save(Mockito.any(Article.class))).thenReturn(mockArticle);

        //when
        ArticleForm articleForm = articleService.update(mockArticle.getId(), articleUpdateRequest1);

        //then
        assertThat(articleForm).usingRecursiveComparison().isEqualTo(Article.toUpdateRequest(mockArticle));
        assertThat(articleForm.getId()).isEqualTo(articleUpdateRequest1.getId());
        assertThat(articleForm.getTitle()).isEqualTo(articleUpdateRequest1.getTitle());
        assertThat(articleForm.getContent()).isEqualTo(articleUpdateRequest1.getContent());
    }

    @DisplayName("특정 게시글 제목만 변경하기")
    @Test
    void UpdateArticleTitle_ReturnUpdatedArticle() {
        //given
        Article mockArticle = article1;
        Mockito.when(articleRepository.findById(mockArticle.getId())).thenReturn(Optional.of(article1));
        Mockito.when(articleRepository.save(Mockito.any(Article.class))).thenReturn(mockArticle);

        //when
        ArticleForm updatedArticle = articleService.update(mockArticle.getId(), articleUpdateRequest2);

        //then
        assertThat(updatedArticle.getId()).isEqualTo(articleUpdateRequest2.getId());
        assertThat(updatedArticle.getTitle()).isEqualTo(articleUpdateRequest2.getTitle());
        assertThat(updatedArticle.getContent()).isEqualTo(mockArticle.getContent());
    }

    @DisplayName("존재하는 특정 게시글 삭제하기")
    @Test
    void DeleteArticle_ReturnDeletedArticle() {
        //given
        Mockito.when(articleRepository.findById(article1.getId())).thenReturn(Optional.of(article1));
        Mockito.doNothing().when(articleRepository).delete(article1);

        //when
        ArticleForm deletedArticleForm = articleService.delete(article1.getId());

        //then
        Mockito.verify(articleRepository).delete(article1);
        assertThat(deletedArticleForm).usingRecursiveComparison().isEqualTo(Article.toForm(article1));
    }

    @DisplayName("존재 하지 않는 특정 게시글 삭제하기")
    @Test
    void DeleteArticleNotExist_ReturnNull() {
        //given
        Mockito.when(articleRepository.findById(article1.getId())).thenReturn(Optional.empty());

        //when

        //then
        assertThatThrownBy(() -> {
            articleService.delete(article1.getId());
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("delete, 존재하지 않는 게시글 입니다.");
    }
}
