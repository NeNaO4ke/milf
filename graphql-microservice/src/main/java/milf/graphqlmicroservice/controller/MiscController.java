package milf.graphqlmicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
@RequiredArgsConstructor
public class MiscController {

    private final LinkedBlockingQueue<String> queue;
    private final Flux<String> kafkaWebProducerFlux;

    @GetMapping("/graphql/test")
    public Flux<String> test(){
        return Flux.fromIterable(queue);
    }

    @GetMapping("/graphql/test/sink")
    public Flux<String> testSink(){
        return kafkaWebProducerFlux;
    }
}
