package milf.graphqlmicroservice.feign;

import milf.graphqlmicroservice.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(name = "user-service")
public interface UserService {

    String prefix = "/user/";

    @GetMapping(prefix + "/test")
    Mono<String> test(@RequestHeader(value = "X-auth-user-id") String userId);

    @GetMapping(prefix + "/username/{username}")
    Mono<User> getUserByUsername(@PathVariable String username);

    @GetMapping(prefix + "/{id}")
    Mono<User> getUserById(@PathVariable String id);

    @PostMapping(prefix + "/usersById")
    Flux<User> getAllUsersWithIds(@RequestBody Flux<String> ids);

    @PostMapping(prefix + "/create")
    Mono<User> createUser(@RequestBody User user);

    @DeleteMapping(prefix + "/{userDeleteId}")
    Mono<Void> deleteUser(@RequestHeader(value = "X-auth-user-id") String userId,
                          @RequestHeader(value = "X-auth-user-roles") String rolesString,
                          @PathVariable String userDeleteId);

}
