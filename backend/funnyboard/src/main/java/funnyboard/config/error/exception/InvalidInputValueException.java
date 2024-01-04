package funnyboard.config.error.exception;

import funnyboard.config.error.ErrorCode;

public class InvalidInputValueException extends BusinessBaseException {
    public InvalidInputValueException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }
    public InvalidInputValueException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
