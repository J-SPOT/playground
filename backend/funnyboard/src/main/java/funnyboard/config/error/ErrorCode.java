package funnyboard.config.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E1", "올바르지 않은 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E2", "잘못된 HTTP 메서드를 호출했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E3", "서버 에러가 발생했습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "E4", "존재하지 않는 엔티티입니다."),

    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "A1", "존재하지 않는 게시글입니다."),
    ARTICLE_CREATION_HAS_ID(HttpStatus.BAD_REQUEST, "A2", "유효한 게시글 id가 아닌 게시글 생성 요청입니다."),
    ARTICLE_UPDATE_NOT_VALID(HttpStatus.BAD_REQUEST, "A3", "수정하려는 게시글 id와 요청하는 게시글 id가 일치하지 않습니다."),

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C1", "존재하지 않는 댓글입니다."),
    COMMENT_CREATION_HAS_ID(HttpStatus.BAD_REQUEST, "C2", "유효한 댓글 id가 아닌 댓글 생성 요청입니다."),
    COMMENT_CREATION_WRONG_ARTICLE_ID(HttpStatus.BAD_REQUEST, "C3", "유효한 게시글 id가 아닌 댓글 생성 요청입니다."),
    COMMENT_UPDATE_NOT_VALID(HttpStatus.BAD_REQUEST, "C4", "수정하려는 댓글 id와 요청하는 댓글id가 일치하지 않습니다.");


    private final String message;
    private final String code;
    private final HttpStatus status;

    ErrorCode(final HttpStatus status, final String message, final String code) {
        this.message = message;
        this.code = code;
        this.status = status;
    }
}