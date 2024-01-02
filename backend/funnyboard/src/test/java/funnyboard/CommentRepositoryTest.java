package funnyboard;

import funnyboard.domain.Article;
import funnyboard.domain.Comment;
import funnyboard.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CommentRepositoryTest {

    @Mock
    CommentRepository commentRepository;

    Article article1;
    Comment comment1, comment2, comment3;

    @BeforeEach
    void setup() {
        article1 = new Article(1L, "제목 1", "내용 1");
        comment1 = new Comment(1L, article1, "작성자 1", "내용 1");
        comment2 = new Comment(2L, article1, "작성자 1", "내용 2");
        comment3 = new Comment(3L, article1, "작성자 2", "내용 3");
    }

    @DisplayName("댓글 저장하기")
    @Test
    void SaveArticle_ReturnSavedArticle() {
        //given
        Mockito.when(commentRepository.save(comment1)).thenReturn(comment1);

        //when
        Comment savedComment1 = commentRepository.save(comment1);

        //then
        assertThat(savedComment1).isNotNull();
        assertThat(savedComment1).isEqualTo(comment1);
    }

    @DisplayName("게시글 id에 해당하는 댓글을 찾기")
    @Test
    void FindById_ReturnSavedArticle() {
        //given
        Mockito.when(commentRepository.findAll()).thenReturn(Arrays.asList(comment1, comment2));

        //when
        List<Comment> findAllComments = commentRepository.findAll();

        //then
        assertThat(findAllComments).isNotNull();
        assertThat(findAllComments.size()).isEqualTo(2);
    }
}
