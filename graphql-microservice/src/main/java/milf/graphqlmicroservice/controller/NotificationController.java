package milf.graphqlmicroservice.controller;

import lombok.RequiredArgsConstructor;
import milf.graphqlmicroservice.domain.Dto.PostNUser;
import milf.graphqlmicroservice.service.GqlEventService;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

import java.util.concurrent.LinkedBlockingQueue;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final GqlEventService gqlEventService;
    private final LinkedBlockingQueue<String> queue;


    @SubscriptionMapping
    public Flux<String> postCreated() {
        return Flux.fromIterable(queue);
    }


}
