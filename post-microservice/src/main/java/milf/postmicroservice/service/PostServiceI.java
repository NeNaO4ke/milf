package milf.postmicroservice.service;

import milf.postmicroservice.domain.Post;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

public interface PostServiceI {
    Flux<Post> findAllPosts();

    @Transactional
    Mono<Post> likeOrDislikePost(String postId, String authorId);

    @Transactional
    Mono<Post> saveOnePostTransactional(Post post);

    Mono<Post> savePost(
            Flux<FilePart> fileParts,
            @Nullable String text,
            @Nullable String url,
            @Nullable String isCommentTo,
            String userUploaderId);

    Flux<Post> getAllByAuthorIdSortedByDate(String id);

    Flux<Post> getAllByAuthorId(String id);

    Mono<Post> getOnePostById(String id);

    Mono<Void> deletePost(String postId, String userId);

    Mono<Post> updatePost(Post post, String userId);

    Flux<Post> getAllCommentsToPost(String postId);
}
