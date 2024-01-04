package funnyboard.domain;

import funnyboard.config.error.exception.comment.CommentCreationHasId;
import funnyboard.config.error.exception.comment.CommentCreationWrongArticleId;
import funnyboard.dto.CommentForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    @Column
    private String nickname;
    @Column
    private String content;

    public static Comment createComment(CommentForm dto, Article article) {
        if (dto.getId() != null)
            throw new CommentCreationHasId();
        if (dto.getArticleId() != article.getId())
            throw new CommentCreationWrongArticleId();
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getContent()
        );
    }

    public void patch(CommentForm dto) {
        if (dto.getNickname() != null)
            this.nickname = dto.getNickname();
        if (dto.getContent() != null)
            this.content = dto.getContent();
    }
}
