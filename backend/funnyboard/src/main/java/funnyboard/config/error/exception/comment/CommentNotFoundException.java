package funnyboard.config.error.exception.comment;

import funnyboard.config.error.ErrorCode;
import funnyboard.config.error.exception.NotFoundException;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
