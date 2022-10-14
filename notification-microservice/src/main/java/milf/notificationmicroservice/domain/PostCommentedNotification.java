package milf.notificationmicroservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document("notification")
public class PostCommentedNotification {

    private final Notifications type = Notifications.POST_COMMENTED;

    private String postId, postAuthorId, userId;
    private Date createdAt;
    private boolean viewed;


}
