package funnyboard.service;

import funnyboard.config.error.exception.article.ArticleNotFoundException;
import funnyboard.config.error.exception.comment.CommentNotFoundException;
import funnyboard.config.error.exception.comment.CommentUpdateIdNotValid;
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
                () -> new ArticleNotFoundException());
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
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CommentNotFoundException());
        if (comment.getId() != dto.getId())
            throw new CommentUpdateIdNotValid();
        comment.patch(dto);
        Comment updated = commentRepository.save(comment);
        return CommentForm.createCommentDto(updated);
    }

    public CommentForm delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CommentNotFoundException());
        commentRepository.delete(comment);
        return CommentForm.createCommentDto(comment);
    }
}
