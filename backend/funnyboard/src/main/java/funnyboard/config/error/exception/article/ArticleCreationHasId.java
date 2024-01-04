package funnyboard.config.error.exception.article;

import funnyboard.config.error.ErrorCode;
import funnyboard.config.error.exception.InvalidInputValueException;

public class ArticleCreationHasId extends InvalidInputValueException {
    public ArticleCreationHasId() {
        super(ErrorCode.ARTICLE_CREATION_HAS_ID);
    }
}
