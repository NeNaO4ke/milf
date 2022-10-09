package milf.usermicroservice.service;

import lombok.RequiredArgsConstructor;
import milf.usermicroservice.domain.User;
import milf.usermicroservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceI{

    private final UserRepository userRepository;

    @Override
    public Mono<User> getUserById(String id) {
        return userRepository.getUserById(id);
    }

    @Override
    public Mono<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
