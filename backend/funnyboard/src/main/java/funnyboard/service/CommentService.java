package funnyboard.service;

import funnyboard.dto.CommentForm;
import funnyboard.entity.Article;
import funnyboard.entity.Comment;
import funnyboard.repository.ArticleRepository;
import funnyboard.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
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
}
