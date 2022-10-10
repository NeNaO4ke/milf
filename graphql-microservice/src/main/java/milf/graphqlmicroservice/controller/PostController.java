package milf.graphqlmicroservice.controller;

import graphql.GraphQLContext;
import lombok.RequiredArgsConstructor;
import milf.graphqlmicroservice.domain.Dto.PostNUser;
import milf.graphqlmicroservice.domain.Post.Post;
import milf.graphqlmicroservice.domain.user.User;
import milf.graphqlmicroservice.service.GqlPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private GqlPostService gqlPostService;


    @QueryMapping
    Flux<PostNUser> getGlobalFeed() {
        return gqlPostService.findAllPostWithUsers();
    }

    @QueryMapping
    Flux<Post> getAllPosts() {
        return gqlPostService.findAllPosts();
    }

    @QueryMapping
    Mono<Post> getOnePost(@Argument String id) {
        return gqlPostService.getOnePost(id);
    }

    @QueryMapping
    Flux<Post> getAllPostsByAuthorId(@Argument String authorId) {
        return gqlPostService.getAllPostsByAuthorId(authorId);
    }

    @QueryMapping
    Mono<PostNUser> getPostWithUser(@Argument String postId) {
        return gqlPostService.getPostWithUser(postId);
    }

    @QueryMapping
    Flux<PostNUser> getTopPostsWithUsers() {
        return gqlPostService.getTopPostsWithUsers();
    }

    @QueryMapping
    Flux<PostNUser> getCommentsToPost(@Argument String postId) {
        return gqlPostService.getCommentsToPostWithUsers(postId);
    }

    @MutationMapping
    Mono<Post> likeOrDislikePost(@Argument String postId, GraphQLContext context) {
        String id = context.getOrDefault("X-auth-user-id", "default");
        return gqlPostService.likeOrDislikePost(postId, id);
    }



}
