package funnyboard.config.error.exception.comment;

import funnyboard.config.error.ErrorCode;
import funnyboard.config.error.exception.InvalidInputValueException;

public class CommentUpdateIdNotValid extends InvalidInputValueException {
    public CommentUpdateIdNotValid() {
        super(ErrorCode.COMMENT_UPDATE_NOT_VALID);
    }
}

