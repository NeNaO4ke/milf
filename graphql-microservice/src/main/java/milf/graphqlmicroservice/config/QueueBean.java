package milf.graphqlmicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class QueueBean {
    @Bean
    LinkedBlockingQueue<String> queue(){
        return new LinkedBlockingQueue<>();
    }

    @Bean
    public Sinks.Many<String> sinkKafkaWebProducer () {
        return Sinks.many().replay().limit(10);
    }

    @Bean
    public Flux<String> kafkaWebProducerFlux () {
        return sinkKafkaWebProducer().asFlux();
    }
}
