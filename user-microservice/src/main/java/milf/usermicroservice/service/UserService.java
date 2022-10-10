package milf.usermicroservice.service;

import lombok.RequiredArgsConstructor;
import milf.usermicroservice.domain.Role;
import milf.usermicroservice.domain.User;
import milf.usermicroservice.repository.UserRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceI {

    private final UserRepository userRepository;

    public Mono<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Mono<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Flux<User> getAllUsersWithIds(Flux<String> ids) {
        return ids.flatMap(this::getUserById);
    }

    public Mono<User> createUser(User user) {
        User newUser = new User(user.getUsername(), user.getPassword());
        return userRepository.save(newUser);
    }

    public Mono<Void> deleteUser(String userDeleteId, String userId, String rolesString) {
        Set<String> roles = Set.of(rolesString.split(" "));

        if(!roles.contains("ADMIN"))
            return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You don`t have such rights"));

        //TODO Log that action

        User deleteUser = new User();
        deleteUser.setId(userDeleteId);
        return userRepository.delete(deleteUser);
    }

}
