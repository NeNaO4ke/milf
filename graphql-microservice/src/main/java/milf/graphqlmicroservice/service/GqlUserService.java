package milf.graphqlmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import milf.graphqlmicroservice.domain.user.User;
import milf.graphqlmicroservice.feign.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GqlUserService {
    @Autowired
    private UserService userService;

    public Mono<User> getUserById(String id){
        return userService.getUserById(id);
    }

}
