package milf.usermicroservice.controller;

import lombok.RequiredArgsConstructor;
import milf.usermicroservice.domain.User;
import milf.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    @Value("${test.value}")
    private String test;

    private final UserService userService;

    @GetMapping("/test")
    public Mono<String> test(){
        return Mono.just("Hello " + test);
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }
}
