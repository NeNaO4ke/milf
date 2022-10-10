package milf.postmicroservice.controller;

import milf.postmicroservice.domain.Post;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostControllerI {
    @GetMapping("/all")
    Flux<Post> getAllPosts();

    @GetMapping("/{id}")
    Mono<Post> getPostById(@PathVariable String id);

    @GetMapping("/author/{id}")
    Flux<Post> getPostsByAuthorId(@PathVariable String id, @RequestParam(value = "unordered", required = false) String orderedByDate);

    @GetMapping("/comments/{postId}")
    Flux<Post> getAllCommentsToPost(@PathVariable String postId);

    @PostMapping("/savePost")
    Mono<Post> savePost(
            @RequestPart(value = "files", required = false) Flux<FilePart> fileParts,
            @RequestPart(value = "text", required = false) String text,
            @RequestPart(value = "link", required = false) String url,
            @RequestPart(value = "isCommentTo", required = false) String isCommentTo,
            @RequestHeader(value = "X-auth-user-id") String userId
    );

    @PostMapping("/like/{postId}")
    Mono<Post> likeOrDislikePost(@PathVariable String postId, @RequestHeader(value = "X-auth-user-id") String authorId);

    @PutMapping("/")
    Mono<Post> updatePost(@RequestBody Post post, @RequestHeader(value = "X-auth-user-id") String authorId);

    @DeleteMapping("/{id}")
    Mono<Void> deletePost(@PathVariable String id, @RequestHeader(value = "X-auth-user-id") String authorId);
}
