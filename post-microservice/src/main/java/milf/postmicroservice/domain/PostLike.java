package milf.postmicroservice.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(of = "userId")
@Data
@NoArgsConstructor
public class PostLike {
    private String userId;
    private Date creationTime;

    public PostLike(String userId) {
        this.userId = userId;
        this.creationTime = new Date();
    }
}
