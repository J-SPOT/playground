package funnyboard.controller;

import funnyboard.dto.CommentForm;
import funnyboard.repository.CommentRepository;
import funnyboard.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "CommentController", description = "댓글 CRUD API")
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    public CommentController(CommentRepository commentRepository, CommentService commentService) {
        this.commentRepository = commentRepository;
        this.commentService = commentService;
    }

    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentForm> create(@PathVariable Long articleId,
                                             @RequestBody CommentForm dto) {
        CommentForm createdDto = commentService.create(articleId, dto);
        return (createdDto != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
