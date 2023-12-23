package funnyboard;

import funnyboard.dto.CommentForm;
import funnyboard.entity.Article;
import funnyboard.entity.Comment;
import funnyboard.repository.ArticleRepository;
import funnyboard.repository.CommentRepository;
import funnyboard.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommentServiceTest {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentService commentService;
    @Autowired
    ArticleRepository articleRepository;

    @Test
    @DisplayName("게시글 댓글 생성하기")
    void 게시글에_댓글_생성하기() {
        //given
        Long articleId = 4L;
        CommentForm dto = new CommentForm(null, articleId, "juny", "hahah!! im jjang!");

        //when
        CommentForm commentForm = commentService.create(articleId, dto);

        //then
        assertThat(commentForm).isNotNull();
        assertThat(commentForm.getId()).isEqualTo(10); // 이런식으로 하면 너무 db data에 종속적인데..
        assertThat(commentForm.getNickname()).isEqualTo(dto.getNickname());
        assertThat(commentForm.getContent()).isEqualTo(dto.getContent());
    }

    @Test
    @DisplayName("게시글 댓글 조회하기")
    void 게시글_댓글_조회하기() {
        //given
        Long articleId = 4L;

        //when
        Article article = new Article(4L, "당신의 인생 영화는?", "댓점");
        List<CommentForm> commentForms = commentService.lookupComments(articleId);

        ArrayList<CommentForm> expected = new ArrayList<>();
        expected.add(new CommentForm(1L, 4L, "juny", "인터스텔라"));
        expected.add(new CommentForm(2L, 4L, "jiny", "1988"));
        expected.add(new CommentForm(3L, 4L, "teacher", "라라랜드"));

        //then
        assertThat(commentForms.toString()).isEqualTo(expected.toString());
    }

    @Test
    @DisplayName("게시글 댓글 수정하기")
    void 게시글_댓글_수정하기() {
        //given

        //when
        Comment expected = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓점"), "케케", "크크");

        CommentForm updated = commentService.update(1L, new CommentForm(1L, 1L, "케케", "크크"));
        Comment actual = commentRepository.findById(1L).orElse(null);

        //then
        assertThat(actual.toString()).isEqualTo(expected.toString());
    }

    @Test
    @DisplayName("게시글 댓글 삭제하기")
    void 게시글_댓글_삭제하기() {
        //given

        //when
        CommentForm deletedData = commentService.delete(3L);
        CommentForm notExistData = commentService.delete(3L);

        //then
        assertThat(deletedData).isNotNull();
        assertThat(notExistData).isNull();
    }

}
