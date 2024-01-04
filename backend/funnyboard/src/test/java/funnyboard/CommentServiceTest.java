package funnyboard;

import funnyboard.config.error.exception.comment.CommentNotFoundException;
import funnyboard.domain.Article;
import funnyboard.domain.Comment;
import funnyboard.dto.CommentForm;
import funnyboard.repository.ArticleRepository;
import funnyboard.repository.CommentRepository;
import funnyboard.service.CommentService;
import org.assertj.core.api.Assertions;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ArticleRepository articleRepository;
    
    @InjectMocks
    private CommentService commentService;

    Article article1, article2;
    Comment comment1, comment2, comment3, beforeUpdateComment, updatedComment, createdComment;
    CommentForm createForm, updateForm;

    @BeforeEach
    void setup() {
        article1 = new Article(1L, "제목 1", "내용 1");
        article2 = new Article(2L, "제목 2", "내용 2");
        comment1 = new Comment(null, article1, "작성자 1", "내용 1");
        comment2 = new Comment(null, article1, "작성자 1", "내용 2");
        comment3 = new Comment(null, article1, "작성자 2", "내용 3");
        beforeUpdateComment = new Comment(1L, article1, "작성자 1", "내용 1");
        updatedComment = new Comment(1L, article1, "작성자 1 수정", "내용 1 수정");

        createdComment = new Comment(1L, article1, "작성자 1", "내용 1");
        updateForm = new CommentForm(1L, article1.getId(), "작성자 1 수정", "내용 1 수정");
        createForm = new CommentForm(null, article1.getId(), "작성자 1", "내용 1");
    }

    @DisplayName("특정 게시글에 모든 댓글 가져오기")
    @Test
    void FindComments_ReturnFindComments() {
        //given
        List<Comment> comments = Arrays.asList(comment1, comment2, comment3);
        Mockito.when(commentRepository.findByArticleId(article1.getId())).thenReturn(comments);

        //when
        List<CommentForm> findCommentsDto = commentService.findAllComments(article1.getId());
        List<Comment> findComments = findCommentsDto.stream()
                .map(comment -> Comment.createComment(comment, article1))
                .collect(Collectors.toList());

        //then
        assertThat(findComments).isNotNull();
        assertThat(findComments).usingRecursiveComparison().isEqualTo(comments);
    }

    @DisplayName("특정 게시글에 특정 댓글 수정하기")
    @Test
    void UpdateComment_ReturnUpdatedComment() {
        //given
        Mockito.when(commentRepository.findById(beforeUpdateComment.getId())).thenReturn(Optional.of(updatedComment));
        Mockito.when(commentRepository.save(updatedComment)).thenReturn(updatedComment);

        //when
        CommentForm updatedForm = commentService.update(beforeUpdateComment.getId(), updateForm);


        //then
        assertThat(updatedForm).isNotNull();
        assertThat(updatedForm).usingRecursiveComparison().isEqualTo(updateForm);
    }

    @DisplayName("특정 댓글 생성하기")
    @Test
    void CreateComment_ReturnCreatedComment() {
        //given
        Mockito.when(articleRepository.findById(article1.getId())).thenReturn(Optional.of(article1));
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comment1);
                
        //when
        CommentForm createdForm = commentService.create(article1.getId(), createForm);

        //then
        assertThat(createdForm).isNotNull();
        assertThat(createdForm).usingRecursiveComparison().isEqualTo(createForm);
    }

    @DisplayName("존재하는 특정 게시글 삭제하기")
    @Test
    void DeleteArticle_ReturnDeletedArticle() {
        //given
        Mockito.when(commentRepository.findById(createdComment.getId())).thenReturn(Optional.of(createdComment));
        Mockito.doNothing().when(commentRepository).delete(createdComment);

        //when
        CommentForm deletedForm = commentService.delete(article1.getId());

        //then
        Mockito.verify(commentRepository).delete(createdComment);
    }
    @DisplayName("존재 하지 않는 특정 게시글 삭제하기")
    @Test
    void DeleteArticleNotExist_ReturnNull() {
        //given
        Mockito.when(commentRepository.findById(comment1.getId())).thenReturn(Optional.empty());

        //when

        //then
        Assertions.assertThatThrownBy(() -> {
            commentService.delete(comment1.getId());
        }).isInstanceOf(CommentNotFoundException.class)
                .hasMessageContaining("C1");
    }
}
