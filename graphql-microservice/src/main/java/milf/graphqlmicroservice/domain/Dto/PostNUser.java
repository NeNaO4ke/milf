package milf.graphqlmicroservice.domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import milf.graphqlmicroservice.domain.Post.Post;
import milf.graphqlmicroservice.domain.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostNUser {
    private User user;
    private Post post;
}
