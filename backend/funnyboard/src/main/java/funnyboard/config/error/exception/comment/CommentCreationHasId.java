package funnyboard.config.error.exception.comment;

import funnyboard.config.error.ErrorCode;
import funnyboard.config.error.exception.InvalidInputValueException;

public class CommentCreationHasId extends InvalidInputValueException {
    public CommentCreationHasId() {
        super(ErrorCode.COMMENT_CREATION_HAS_ID);
    }
}
