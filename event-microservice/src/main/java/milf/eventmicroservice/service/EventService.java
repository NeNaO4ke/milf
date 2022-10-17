package milf.eventmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final StreamBridge streamBridge;

    private final KafkaTemplate<String, byte[]> kafkaTemplate;


    public Mono<Void> push() {

        return Mono.just(streamBridge.send("test-out-0", "Hello from " + new Date())).log("Send!").then();
    }


    public Mono<Void> sendMessage() {
        String s = "Sending: " + new Date();
        return Mono.just(kafkaTemplate.send("testQueue", s.getBytes() )).then();
    }

//    @Bean
//    public Supplier<Flux<String>> test() {
//        return () -> Flux.interval(Duration.ofMillis(3000))
//                .onBackpressureDrop()
//                .map(aLong -> "Hello from " + aLong)
//                .doOnNext(passenger -> log.info("generated: {}", passenger));
//    }

}
