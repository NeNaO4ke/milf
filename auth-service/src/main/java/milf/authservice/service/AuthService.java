package milf.authservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.lang.Maps;
import lombok.RequiredArgsConstructor;
import milf.authservice.config.JwtUtil;
import milf.authservice.domain.CredentialsDTO;
import milf.authservice.domain.UserDTO;
import milf.authservice.exception.AppException;
import milf.authservice.feign.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Base64;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Value("${jwt.secret}")
    private String secret;

    public Mono<ResponseEntity<UserDTO>> signIn(CredentialsDTO credentialsDTO) {
        return userService.getUserByUsername(credentialsDTO.getUsername())
                .map(userDTO -> {
                    userDTO.setToken(jwtUtil.generateToken(userDTO));
                    return ResponseEntity.ok(userDTO);
                }).switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
    }

    public Mono<UserDTO> validateToken(String token) throws Exception {
        String username;
        try {
            username = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            throw new AppException("JWT malfunction", HttpStatus.UNAUTHORIZED);
        }
        if (username != null && jwtUtil.validateToken(token)) {
            UserDTO user = Jwts.parserBuilder()
                    .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                    .deserializeJsonWith(new JacksonDeserializer(Maps.of("user", UserDTO.class).build()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("user", UserDTO.class);
            return Mono.just(user);

        }
        throw new AppException("JWT malfunction", HttpStatus.UNAUTHORIZED);
    }
}
