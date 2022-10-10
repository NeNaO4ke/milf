package milf.postmicroservice.repository;

import milf.postmicroservice.domain.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository extends ReactiveMongoRepository<Post, String> {

    Flux<Post> findPostsByAuthorId(String id);
    Flux<Post> findPostsByAuthorIdOrderByDateDesc(String id);

    Flux<Post> getPostByIsCommentToIs(String id);

}

