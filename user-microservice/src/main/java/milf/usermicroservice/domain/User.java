package milf.usermicroservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "usr")
@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value = "password", allowGetters = true)
public class User {

    @Id
    private String id;

    private String username;

    private String password;

    private String avatar;

    private String description;

    private Set<Role> roles;

    public User(){

    }

    public User(String username, String password){
        this.id = null;
        this.username = username;
        this.avatar = null;
        this.description = null;
        this.password = password;
        this.roles = Set.of(Role.USER);
    }

}
