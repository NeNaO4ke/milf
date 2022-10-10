package milf.postmicroservice.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import milf.postmicroservice.domain.DTO.MetaDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Document(collection = "post")
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    private String id;
    private String text;
    private Integer likesCount;
    private Integer commentsCount;
    private Set<PostLike> likes;
    private String isCommentTo;
    private Set<String> commentIds;
    private Set<String> fileLocations;
    private String authorId;
    private MetaDto metaDto;
    private Date date;

    public Post() {
        this.id = null;
        this.likesCount = 0;
        this.commentsCount = 0;
        this.likes = new HashSet<>();
        this.commentIds = new HashSet<>();
        this.fileLocations = new HashSet<>();
        this.isCommentTo = null;
        this.date = new Date();
    }

}
