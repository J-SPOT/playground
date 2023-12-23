package funnyboard.controller;

import funnyboard.dto.CommentForm;
import funnyboard.repository.CommentRepository;
import funnyboard.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentForm>> lookupComments(@PathVariable Long articleId) {
        List<CommentForm> dtos = commentService.lookupComments(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentForm> update(@PathVariable Long id,
                                              @RequestBody CommentForm dto) {
        CommentForm updatedDto = commentService.update(id, dto);
        return (updatedDto != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updatedDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentForm> delete(@PathVariable Long id) {
        CommentForm deletedForm = commentService.delete(id);
        return (deletedForm != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
