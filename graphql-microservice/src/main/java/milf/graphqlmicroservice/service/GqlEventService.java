package milf.graphqlmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class GqlEventService {


    private final LinkedBlockingQueue<String> queue;
    private final Sinks.Many<String> sinkKafkaWebProducer;
    @Bean
    public Consumer<Flux<String>> test() {
        return flux ->
                flux.map(sinkKafkaWebProducer::tryEmitNext)
                .subscribe(s -> log.error("Received: {}", s));
    }
}
