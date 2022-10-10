package milf.usermicroservice.controller;

import milf.usermicroservice.domain.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserControllerI {
    @GetMapping(value = "/test")
    Mono<String> test(@RequestHeader(value = "X-auth-user-id") String userId);

    @GetMapping("/username/{username}")
    Mono<User> getUserByUsername(@PathVariable String username);

    @GetMapping("/{id}")
    Mono<User> getUserById(@PathVariable String id);

    @PostMapping("/usersById")
    Flux<User> getAllUsersWithIds(@RequestBody Flux<String> ids);

    @PostMapping("/create")
    Mono<User> createUser(@RequestBody User user);

    @DeleteMapping("/{userDeleteId}")
    Mono<Void> deleteUser(@RequestHeader(value = "X-auth-user-id") String userId,
                          @RequestHeader(value = "X-auth-user-roles") String rolesString,
                          @PathVariable String userDeleteId);
}
