package funnyboard.config.error.exception.article;

import funnyboard.config.error.ErrorCode;
import funnyboard.config.error.exception.InvalidInputValueException;

public class ArticleUpdateIdNotValid extends InvalidInputValueException {
    public ArticleUpdateIdNotValid() {
        super(ErrorCode.ARTICLE_UPDATE_NOT_VALID);
    }
}
