package funnyboard.domain;

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
        if (dto.getId() != null) {
            throw new IllegalArgumentException("createComment, 댓글 생성 실패! 댓글의 아이디가 없어야 합니다.");
        }
        if (dto.getArticleId() != article.getId()) {
            System.out.println("dto.getArticleId() = " + dto.getArticleId());
            System.out.println("article.getId() = " + article.getId());
            throw new IllegalArgumentException("createComment, 댓글 생성 실패! 게시글의 아이디가 잘못되었습니다.");
        }
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
