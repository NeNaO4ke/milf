package milf.usermicroservice.repository;

import milf.usermicroservice.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> getUserById(String id);
    Mono<User> getUserByUsername(String username);
}
