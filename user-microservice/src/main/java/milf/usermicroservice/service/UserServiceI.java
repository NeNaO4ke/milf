package milf.usermicroservice.service;

import milf.usermicroservice.domain.User;
import reactor.core.publisher.Mono;

public interface UserServiceI {

    Mono<User> getUserById(String id);
}
