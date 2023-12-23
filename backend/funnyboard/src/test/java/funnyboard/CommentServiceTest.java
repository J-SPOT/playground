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

        Article article = articleRepository.findById(articleId).orElse(null);
        Comment comment = Comment.createComment(dto, article);
        Comment savedComment = commentRepository.save(comment);
        //when
        System.out.println("savedComment.toString() = " + savedComment.toString());

        //then
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getId()).isEqualTo(10); // 이런식으로 하면 너무 db data에 종속적인데..
        assertThat(savedComment.getNickname()).isEqualTo(dto.getNickname());
        assertThat(savedComment.getContent()).isEqualTo(dto.getContent());
    }

    @Test
    @DisplayName("게시글 댓글 조회하기")
    void 게시글_댓글_조회하기() {
        //given


        //when

        //then
        assertThat(true).isEqualTo(true);
    }

    @Test
    @DisplayName("게시글 댓글 수정하기")
    void 게시글_댓글_수정하기() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("게시글 댓글 삭제하기")
    void 게시글_댓글_삭제하기() {
        //given

        //when

        //then
    }

}
