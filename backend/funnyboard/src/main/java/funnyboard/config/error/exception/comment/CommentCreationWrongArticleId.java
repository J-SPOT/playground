package funnyboard.config.error.exception.comment;

import funnyboard.config.error.ErrorCode;
import funnyboard.config.error.exception.InvalidInputValueException;

public class CommentCreationWrongArticleId extends InvalidInputValueException {

    public CommentCreationWrongArticleId() {
        super(ErrorCode.COMMENT_CREATION_WRONG_ARTICLE_ID);
    }
}
