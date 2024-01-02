package funnyboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import funnyboard.controller.CommentController;
import funnyboard.domain.Article;
import funnyboard.dto.CommentForm;
import funnyboard.service.CommentService;
import org.hamcrest.Matchers;
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
import java.util.List;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    ObjectMapper objectMapper;

    Article article1;
    CommentForm createdCommentForm1, createdCommentForm2, createForm;
    @BeforeEach
    void setup() {
        article1 = new Article(1L, "제목 1", "내용 1");
        createdCommentForm1 = new CommentForm(1L, article1.getId(), "작성자 1", "내용 1");
        createdCommentForm2 = new CommentForm(2L, article1.getId(), "작성자 1", "내용 2");
        createForm = new CommentForm(null, article1.getId(), "작성자 1", "내용 1");

    }

    @DisplayName("특정 게시글에 모든 댓글 가져오기")
    @Test
    void FindAllComments_ReturnFindComments() throws Exception {
        //given
        List<CommentForm> createdForms = Arrays.asList(createdCommentForm1, createdCommentForm2);
        Mockito.when(commentService.findAllComments(article1.getId())).thenReturn(createdForms);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/articles/{articleId}/comments", article1.getId())
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(createdCommentForm1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].articleId").value(createdCommentForm1.getArticleId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nickname").value(createdCommentForm1.getNickname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value(createdCommentForm1.getContent()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(createdCommentForm2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].articleId").value(createdCommentForm2.getArticleId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nickname").value(createdCommentForm2.getNickname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value(createdCommentForm2.getContent()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @DisplayName("특정 게시글에 댓글 생성하기")
    @Test
    void CreateComment_ReturnCreatedComment() throws Exception {
        //given
        Mockito.when(commentService.create(Mockito.eq(article1.getId()), Mockito.any(CommentForm.class))).thenReturn(createdCommentForm1);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/articles/{articleId}/comments", article1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createForm)));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(createdCommentForm1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.articleId").value(createdCommentForm1.getArticleId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value(createdCommentForm1.getNickname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(createdCommentForm1.getContent()));
    }
}
