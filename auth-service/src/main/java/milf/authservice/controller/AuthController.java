package milf.authservice.controller;


import lombok.RequiredArgsConstructor;
import milf.authservice.domain.CredentialsDTO;
import milf.authservice.domain.UserDTO;
import milf.authservice.exception.AppException;
import milf.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("Hello world");
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<UserDTO>> login(@RequestBody CredentialsDTO credentialsDTO) {
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
