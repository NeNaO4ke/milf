package milf.usermicroservice.controller;

import lombok.RequiredArgsConstructor;
import milf.usermicroservice.domain.User;
import milf.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


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
}
