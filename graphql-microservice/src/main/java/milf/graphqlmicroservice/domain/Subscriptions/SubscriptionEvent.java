package milf.graphqlmicroservice.domain.Subscriptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionEvent {

    private EventTypes eventType;
    private Object body;
}
