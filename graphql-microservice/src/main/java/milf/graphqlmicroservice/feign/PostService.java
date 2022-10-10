package milf.graphqlmicroservice.feign;

import milf.graphqlmicroservice.domain.Post.Post;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(name = "post-service")
public interface PostService {

    String prefix = "/post/";
    @GetMapping(prefix + "/all")
    Flux<Post> getAllPosts();

    @GetMapping(prefix + "/{id}")
    Mono<Post> getPostById(@PathVariable String id);

    @GetMapping(prefix + "/author/{id}")
    Flux<Post> getPostsByAuthorId(@PathVariable String id, @RequestParam(value = "unordered", required = false) String orderedByDate);

    @GetMapping(prefix + "/comments/{postId}")
    Flux<Post> getAllCommentsToPost(@PathVariable String postId);

    @PostMapping(prefix + "/like/{postId}")
    Mono<Post> likeOrDislikePost(@PathVariable String postId, @RequestHeader(value = "X-auth-user-id") String authorId);

    @PutMapping(prefix + "/")
    Mono<Post> updatePost(@RequestBody Post post, @RequestHeader(value = "X-auth-user-id") String authorId);

    @DeleteMapping(prefix + "/{id}")
    Mono<Void> deletePost(@PathVariable String id, @RequestHeader(value = "X-auth-user-id") String authorId);
}
