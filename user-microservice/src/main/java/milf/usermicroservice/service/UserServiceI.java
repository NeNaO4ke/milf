package milf.usermicroservice.service;

import milf.usermicroservice.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserServiceI {

    Mono<User> getUserById(String id);

    Mono<User> getUserByUsername(String username);

    Flux<User> getAllUsersWithIds(Flux<String> ids);

    Mono<User> createUser(User user);
    Mono<Void> deleteUser(String userDeleteId, String userId, String rolesString);
}
