package milf.authservice.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milf.authservice.domain.CredentialsDTO;
import milf.authservice.domain.UserDTO;
import milf.authservice.exception.AppException;
import milf.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    public Mono<String> test() {
        log.error("Ping!");
        return Mono.just("Hello world");
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody CredentialsDTO credentialsDTO) {
        return authService.signIn(credentialsDTO);
    }

    @GetMapping("/validateToken")
    public Mono<ResponseEntity<?>> validateToken(@RequestParam String token) throws Exception {
        try {
            return Mono.just(
                    ResponseEntity.ok(authService.validateToken(token))
            );
        } catch (AppException e) {
            return Mono.just(
                    ResponseEntity.status(e.getStatus()).body(e.getMessage()));
        }
    }

}
