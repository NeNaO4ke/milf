package milf.graphqlmicroservice.controller;

import lombok.RequiredArgsConstructor;
import milf.graphqlmicroservice.domain.Dto.PostNUser;
import milf.graphqlmicroservice.service.GqlNotificationService;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final GqlNotificationService gqlNotificationService;

    @SubscriptionMapping
    public Flux<String> postCreated() {
        return null;
    }

}
