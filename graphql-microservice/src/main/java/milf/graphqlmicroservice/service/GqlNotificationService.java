package milf.graphqlmicroservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class GqlNotificationService {

    @Bean
    public Function<Flux<String>, Mono<Void>> consumer() {
        return Flux::then;
    }
}
