package funnyboard.service;

import funnyboard.dto.CommentForm;
import funnyboard.domain.Article;
import funnyboard.domain.Comment;
import funnyboard.repository.ArticleRepository;
import funnyboard.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    public CommentForm create(Long articleId, CommentForm dto) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("CommentService create, 댓글 생성 실패! 게시물이 없습니다.")
        );
        if (article == null)
            return null;
        Comment comment = Comment.createComment(dto, article);
        Comment created = commentRepository.save(comment);
        return CommentForm.createCommentDto(created);
    }

    public List<CommentForm> findAllComments(Long articleId) {
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentForm.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    public CommentForm update(Long id, CommentForm dto) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            log.error("update, 댓글 수정 실패! 대상 댓글이 등록되어 있지 않습니다.");
            return null;
        } else if (comment.getId() != dto.getId()) {
            log.error("patch, 댓글 수정 실패! id가 올바르지 않습니다.");
            return null;
        }
        comment.patch(dto);
        Comment updated = commentRepository.save(comment);
        return CommentForm.createCommentDto(updated);
    }

    public CommentForm delete(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            throw new IllegalArgumentException("delete, 댓글 삭제 실패! 존재 하지 않는 댓글입니다.");
        }
        commentRepository.delete(comment);
        return CommentForm.createCommentDto(comment);
    }
}
