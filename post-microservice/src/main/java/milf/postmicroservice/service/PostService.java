package milf.postmicroservice.service;

import lombok.RequiredArgsConstructor;
import milf.postmicroservice.domain.Post;
import milf.postmicroservice.domain.PostLike;
import milf.postmicroservice.repository.PostRepository;
import milf.postmicroservice.utils.GenericExtFilter;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService implements PostServiceI {


    private final ReactiveMongoTemplate template;
    private final PostRepository postRepository;

    public Flux<Post> findAllPosts() {
        return postRepository.findAll();
    }


    @Transactional
    public Mono<Post> likeOrDislikePost(String postId, String authorId) {
        return getOnePostById(postId)
                .flatMap(post -> {
                    Optional<PostLike> pl = post.getLikes().stream()
                            .filter(postLike -> postLike.getUserId().equals(authorId)).findFirst();
                    if (pl.isEmpty()) {
                        post.getLikes().add(new PostLike(authorId));
                        post.setLikesCount(post.getLikes().size());
                        // TODO Event sourcing via Websocket
                      /*  return userService.getOneById(authorId)
                                .doOnSuccess(user -> subscriptionService.postLiked(user, post)).then(saveOne(post));*/
                        return postRepository.save(post);
                    } else {
                        post.getLikes().remove(pl.get());
                        post.setLikesCount(post.getLikes().size());
                        return postRepository.save(post);
                    }
                });
    }

    @Transactional
    public Mono<Post> saveOnePostTransactional(Post post) {
        if (post.getIsCommentTo() == null) {
            return postRepository.save(post)
                    //TODO Event sourcing via Websocket
/*                    .flatMap(post1 ->
                            userService.getOneById(post1.getAuthorId())
                                    .doOnSuccess(user -> subscriptionService.postCreated(user, post1)).then(Mono.just(post1)))*/
                    ;
        } else {
            Update update = new Update();
            update.inc("commentsCount");

            return postRepository.save(post)
                    .flatMap(post1 ->
                            template.findAndModify(
                                            Query.query(Criteria.where("id").is(post1.getIsCommentTo())),
                                            update.push("commentIds", post1.getId()),
                                            FindAndModifyOptions.options().returnNew(true), Post.class)

                                    // TODO Event sourcing via Websocket
/*                                    .flatMap(updatedPost -> userService.getOneById(post1.getAuthorId())
                                            .doOnSuccess(user -> subscriptionService.commentAdded(user, post1, updatedPost.getAuthorId())))*/
                                    .then(Mono.just(post1)));
        }
    }

    public Mono<Post> savePost(
            Flux<FilePart> fileParts,
            @Nullable String text,
            @Nullable String url,
            @Nullable String isCommentTo,
            String userUploaderId) {

        GenericExtFilter genericExtFilter = new GenericExtFilter("png", "jpeg", "jpg", "gif", "webp");

        Post post = new Post();
        post.setAuthorId(userUploaderId);

        if (isCommentTo != null)
            post.setIsCommentTo(isCommentTo);

        if (StringUtils.isEmpty(text))
            text = null;
        else post.setText(text);

        if (StringUtils.isEmpty(url))
            url = null;

        Mono<String> safeString = Mono.justOrEmpty(text);
        Mono<String> safeUrl = Mono.justOrEmpty(url);

        return safeString.hasElement()
                .flatMap(isStringNotEmpty -> fileParts.hasElements()
                        .flatMapMany(isAnyFilePresent -> {
                            if (!isAnyFilePresent && !isStringNotEmpty)
                                return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your data is empty!"));
                            return fileParts;
                        })
                        .take(6)
                        .filter(filePart -> genericExtFilter.accept(filePart.filename()))
                        .map(filePart -> {
                            String uuidFile = UUID.randomUUID().toString();
                            String resultFilename = uuidFile + "." + filePart.filename();
                            //         post.getFileLocations().add(uploadPath + resultFilename);
                            return filePart;
                        })
                        .buffer()
                        // TODO Service for uploading files to CMS
/*                        .flatMap(fp -> {
                        String uuidFile = UUID.randomUUID().toString();
                        String resultFilename = uuidFile + "." + fp.filename();
                        post.getFileLocations().add(uploadPath + resultFilename);
                        return fp.transferTo(new File(uploadPath + resultFilename));
                    })*/
                        .then(safeUrl)
                        // TODO Service for parsing HTML
/*                        .flatMap(url1 -> {
                        if (Objects.equals(url1, ""))
                            return Mono.empty();
                        return webClientService.getMetaDtoMono(url1);
                    })
                    .map(metaDto -> {
                        post.setMetaDto(metaDto);
                        return Mono.empty();
                    })*/
                        .then(Mono.just(post))
                        .flatMap(this::saveOnePostTransactional));
    }

    public Flux<Post> getAllByAuthorIdSortedByDate(String id) {
        return postRepository.findPostsByAuthorIdOrderByDateDesc(id);
    }

    public Flux<Post> getAllByAuthorId(String id) {
        return postRepository.findPostsByAuthorId(id);
    }


    public Mono<Post> getOnePostById(String id) {
        return postRepository.findById(id);
    }

    public Mono<Void> deletePost(String postId, String userId) {
        return postRepository.findById(postId)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Such post is not found"))))
                .flatMap(post -> {
                    if (post.getAuthorId().equals(userId))
                        return postRepository.delete(post);
                    else
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete posts that is not yours:)"));
                });
    }

    public Mono<Post> updatePost(Post post, String userId) {
        return postRepository.findById(post.getId())
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Such post is not found"))))
                .flatMap(post1 -> {
                    if (post.getAuthorId().equals(userId))
                        return postRepository.save(post);
                    else
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete posts that is not yours:)"));
                });
    }

    public Flux<Post> getAllCommentsToPost(String postId) {
        return postRepository.getPostByIsCommentToIs(postId);
    }
}
