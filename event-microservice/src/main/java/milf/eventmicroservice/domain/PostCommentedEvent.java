package milf.eventmicroservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document("notification")
public class PostCommentedEvent {

    private final Events type = Events.POST_COMMENTED;

    private String postId, postAuthorId, userId;
    private Date createdAt;
    private boolean viewed;


}
