package funnyboard.config.error.exception.article;

import funnyboard.config.error.ErrorCode;
import funnyboard.config.error.exception.NotFoundException;

public class ArticleNotFoundException extends NotFoundException {
    public ArticleNotFoundException() {
        super(ErrorCode.ARTICLE_NOT_FOUND);
    }
}
