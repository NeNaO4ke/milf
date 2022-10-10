package milf.postmicroservice.controller;

import lombok.RequiredArgsConstructor;
import milf.postmicroservice.domain.Post;
import milf.postmicroservice.service.PostService;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController implements PostControllerI {

    private final PostService postService;

    @GetMapping("/all")
    public Flux<Post> getAllPosts() {
        return postService.findAllPosts();
    }

    @GetMapping("/{id}")
    public Mono<Post> getPostById(@PathVariable String id) {
        return postService.getOnePostById(id);
    }

    @GetMapping("/author/{id}")
    public Flux<Post> getPostsByAuthorId(@PathVariable String id, @RequestParam(value = "unordered", required = false) String orderedByDate) {
        if (orderedByDate.equals("false"))
            return postService.getAllByAuthorId(id);
        else
            return postService.getAllByAuthorIdSortedByDate(id);
    }

    @GetMapping("/comments/{postId}")
    public Flux<Post> getAllCommentsToPost(@PathVariable String postId){
        return postService.getAllCommentsToPost(postId);
    }

    @PostMapping("/savePost")
    public Mono<Post> savePost(
            @RequestPart(value = "files", required = false) Flux<FilePart> fileParts,
            @RequestPart(value = "text", required = false) String text,
            @RequestPart(value = "link", required = false) String url,
            @RequestPart(value = "isCommentTo", required = false) String isCommentTo,
            @RequestHeader(value = "X-auth-user-id") String userId
    ) {
        return postService.savePost(fileParts, text, url, isCommentTo, userId);
    }

    @PostMapping("/like/{postId}")
    public Mono<Post> likeOrDislikePost(@PathVariable String postId, @RequestHeader(value = "X-auth-user-id") String authorId) {
        return postService.likeOrDislikePost(postId, authorId);
    }

    @PutMapping("/")
    public Mono<Post> updatePost(@RequestBody Post post, @RequestHeader(value = "X-auth-user-id") String authorId){
        return postService.updatePost(post, authorId);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePost(@PathVariable String id, @RequestHeader(value = "X-auth-user-id") String authorId){
        return postService.deletePost(id, authorId);
    }



}
