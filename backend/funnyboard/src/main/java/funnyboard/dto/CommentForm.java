package funnyboard.dto;

import funnyboard.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentForm {
    private Long id;
    private Long articleId;
    private String nickname;
    private String content;

    public static CommentForm createCommentDto(Comment comment) {
        return new CommentForm(
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getContent()
        );
    }
}
