package milf.graphqlmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import milf.graphqlmicroservice.domain.Dto.PostNUser;
import milf.graphqlmicroservice.domain.Post.Post;
import milf.graphqlmicroservice.domain.user.User;
import milf.graphqlmicroservice.feign.PostService;
import milf.graphqlmicroservice.feign.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class GqlPostService {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    public Flux<PostNUser> getTopPostsWithUsers() {
        return findAllPostWithUsers().sort(Comparator.comparing(obj -> obj.getPost().getLikesCount(), Comparator.reverseOrder()));
    }

    public Mono<PostNUser> getPostWithUser(String postId) {
        return postService.getPostById(postId)
                .flatMap(post -> Mono.zip(userService.getUserById(post.getAuthorId()), Mono.just(post), PostNUser::new));

    }

    public Flux<PostNUser> getCommentsToPostWithUsers(String postId) {
        Flux<Post> allPosts = postService.getAllCommentsToPost(postId).cache();

        return getPostNUserFlux(allPosts);
    }

    private Flux<PostNUser> getPostNUserFlux(Flux<Post> allPosts) {
        Flux<String> allAuthorsIds = allPosts
                .map(Post::getAuthorId)
                .distinct();

        Flux<User> allUsers = userService.getAllUsersWithIds(allAuthorsIds).cache();

        return allPosts
                .flatMap(post -> Mono.zip(getFirstOccurrence(allUsers, post), Mono.just(post), PostNUser::new))
                .sort(Comparator.comparing(obj -> obj.getPost().getDate(), Comparator.reverseOrder()));
    }

    public Flux<PostNUser> findAllPostWithUsers() {

        Flux<Post> allPosts = postService.getAllPosts().filter(post -> post.getIsCommentTo() == null).cache();

        return getPostNUserFlux(allPosts);
    }

    private Mono<User> getFirstOccurrence(Flux<User> allAuthors, Post post) {
        return allAuthors
                .filter(author -> author.getId().equals(post.getAuthorId()))
                .next();
    }

    public Flux<Post> findAllPosts() {
        return postService.getAllPosts();
    }

    public Mono<Post> getOnePost(String id) {
        return postService.getPostById(id);
    }

    public Flux<Post> getAllPostsByAuthorId(String authorId) {
        return postService.getPostsByAuthorId(authorId, "true");
    }

    public Mono<Post> likeOrDislikePost(String postId, String authorId) {
        return postService.likeOrDislikePost(postId, authorId);
    }


}
