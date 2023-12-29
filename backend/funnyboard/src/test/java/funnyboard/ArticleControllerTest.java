package funnyboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import funnyboard.controller.ArticleController;
import funnyboard.domain.Article;
import funnyboard.dto.ArticleForm;
import funnyboard.dto.ArticleUpdateRequest;
import funnyboard.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Autowired
    ObjectMapper objectMapper;

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

    @DisplayName("모든 게시글을 조회하기")
    @Test
    void FindAllArticles_ReturnFindArticles() throws Exception {
        //given
        Mockito.when(articleService.findAllArticles()).thenReturn(Arrays.asList(articleForm1, articleForm2));

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/articles")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("제목 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("내용 1"));
    }

    @DisplayName("특정 게시글을 조회하기")
    @Test
    void FindArticleById_ReturnFindArticle() throws Exception {
        //given
        Mockito.when(articleService.findArticleById(article1.getId())).thenReturn(articleForm1);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/articles/{id}", article1.getId()));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("제목 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("내용 1"));
    }


    @DisplayName("존재하는 특정 게시글 삭제하기")
    @Test
    void DeleteArticle_ReturnDeletedArticle() throws Exception {
        //given
        Mockito.when(articleService.delete(article1.getId())).thenReturn(articleForm1);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/articles/{id}", article1.getId()));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @DisplayName("존재 하지 않는 특정 게시글을 삭제하기")
    @Test
    void DeleteArticleById_ReturnDeleteArticle() throws Exception {
        //given

        Mockito.when(articleService.delete(article1.getId())).thenThrow(new IllegalArgumentException());


        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/articles/{id}", article1.getId()));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
