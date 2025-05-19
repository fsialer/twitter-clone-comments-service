package com.fernando.ms.comments.app.infraestructure.adapter.input.rest;

import com.fernando.ms.comments.app.application.ports.input.CommentDataInputPort;
import com.fernando.ms.comments.app.application.ports.input.CommentInputPort;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper.CommentDataRestMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper.CommentRestMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentDataRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.UpdateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentUserResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.ExistsCommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/comments")
@Tag(name = "Comments", description = "Operations related to comment")
public class CommentRestAdapter {
    private final CommentInputPort commentInputPort;
    private final CommentRestMapper commentRestMapper;
    private final CommentDataInputPort commentDataInputPort;
    private final CommentDataRestMapper commentDataRestMapper;

    @GetMapping
    @Operation(summary = "Find all comments")
    @ApiResponse(responseCode = "200", description = "Found all comments")
    public Flux<CommentResponse> findAll(){
        return  commentRestMapper.toCommentsResponse(commentInputPort.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find comment by id")
    @ApiResponse(responseCode = "200", description = "Found comment by id")
    public Mono<ResponseEntity<CommentResponse>> findById(@PathVariable("id") String id){
        return commentInputPort.findById(id)
                .flatMap(comment ->Mono.just(ResponseEntity.ok().body(commentRestMapper.toCommentResponse(comment))));
    }

    @PostMapping
    @Operation(summary = "Save comment by user")
    @ApiResponse(responseCode = "201", description = "Saved comment by user")
    public Mono<ResponseEntity<CommentResponse>> save(@RequestHeader("X-User-Id") String userId,
                                                      @Valid @RequestBody CreateCommentRequest rq){
        return commentInputPort.save(commentRestMapper.toComment(userId,rq))
                .flatMap(comment -> {
                    String location = "/comments/".concat(comment.getId());
                    return Mono.just(ResponseEntity.created(URI.create(location)).body(commentRestMapper.toCommentResponse(comment)));
                });
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update comment by id")
    @ApiResponse(responseCode = "200", description = "Updated comment by id")
    public Mono<ResponseEntity<CommentResponse>> update(@PathVariable("id") String id,@Valid @RequestBody UpdateCommentRequest rq){
        return commentInputPort.update(id,commentRestMapper.toComment(rq))
                .flatMap(comment->Mono.just(ResponseEntity.ok().body(commentRestMapper.toCommentResponse(comment))));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete comment by id")
    @ApiResponse(responseCode ="204", description = "Deleted comment by id")
    public Mono<Void> delete(@PathVariable("id") String id){
        return commentInputPort.delete(id);
    }

    @GetMapping("/{idPost}/post")
    @Operation(summary = "find all comment by post")
    @ApiResponse(responseCode = "200", description = "find all comment by post")
    public Flux<CommentUserResponse> findAllByPostId(@PathVariable("idPost") String postId,
                                                     @RequestParam("page") @DefaultValue("0") int page,
                                                     @RequestParam("size") @DefaultValue("20")  int size){
        return commentRestMapper.toCommentsAuthorResponse(commentInputPort.findAllByPostId(postId,page,size));
    }

    @GetMapping("/{id}/verify")
    @Operation(summary = "Verify comment by id")
    @ApiResponse(responseCode ="200", description = "Exists comment by id")
    public Mono<ResponseEntity<ExistsCommentResponse>> verify(@PathVariable("id") String id){
        return commentInputPort.verifyById(id)
                .flatMap(exists-> Mono.just(ResponseEntity.ok().body(commentRestMapper.toExistsCommentResponse(exists))));
    }

    @PostMapping("/data")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Save comment data by user")
    @ApiResponse(responseCode ="201", description = "Saved comment data by user")
    public Mono<Void> saveData(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody CreateCommentDataRequest rq
    ) {
        return commentDataInputPort.save(commentDataRestMapper.toCommentData(userId,rq));
    }

    @DeleteMapping("/data/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode ="204", description = "Deleted comment data by id")
    @Operation(summary = "Delete comment data by id")
    public Mono<Void> deleteData(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String id
    ) {
        return commentDataInputPort.delete(id);
    }
}
