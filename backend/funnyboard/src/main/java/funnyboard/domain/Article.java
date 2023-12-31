package funnyboard.domain;


import funnyboard.dto.ArticleForm;
import funnyboard.dto.ArticleUpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    public static ArticleForm toForm(Article article) {
        return new ArticleForm(article.getId(), article.getTitle(), article.getContent());
    }

    public static ArticleUpdateRequest toUpdateRequest(Article article) {
        return new ArticleUpdateRequest(article.getId(), article.getTitle(), article.getContent());
    }

    public void patch(ArticleUpdateRequest dto) {
        if (dto.getTitle() != null) {
            this.title = dto.getTitle();
        }
        if (dto.getContent() != null) {
            this.content = dto.getContent();
        }
    }
}
