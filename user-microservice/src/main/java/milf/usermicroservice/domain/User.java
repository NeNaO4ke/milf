package milf.usermicroservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "usr")
@Data
@AllArgsConstructor
public class User {

    @Id
    private String id;

    private String username;

    @JsonIgnore
    private String password;

    private String avatar;

    private String description;

    private Set<Role> roles;

    public User(){

    }

    public User(String id, String username){
        this.id = id;
        this.username = username;
        this.avatar = null;
        this.description = null;
        this.password = null;
        this.roles = Set.of(Role.USER);
    }


}
