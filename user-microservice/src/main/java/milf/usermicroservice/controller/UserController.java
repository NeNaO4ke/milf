package milf.usermicroservice.controller;

import lombok.RequiredArgsConstructor;
import milf.usermicroservice.domain.User;
import milf.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserControllerI {


    @Value("${test.value}")
    private String test;

    private final UserService userService;

    @GetMapping(value = "/test")
    public Mono<String> test(@RequestHeader(value = "X-auth-user-id") String userId) {
        return Mono.just("Hello " + userId);
    }


    @GetMapping("/username/{username}")
    public Mono<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping("/usersById")
    public Flux<User> getAllUsersWithIds(@RequestBody Flux<String> ids) {
        return userService.getAllUsersWithIds(ids);
    }

    @PostMapping("/create")
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{userDeleteId}")
    public Mono<Void> deleteUser(@RequestHeader(value = "X-auth-user-id") String userId,
                                 @RequestHeader(value = "X-auth-user-roles") String rolesString,
                                 @PathVariable String userDeleteId) {
        return userService.deleteUser(userDeleteId, userId, rolesString);
    }
}
