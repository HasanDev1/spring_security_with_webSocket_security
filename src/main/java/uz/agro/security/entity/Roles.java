package uz.agro.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.agro.security.entity.Users;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Roles implements GrantedAuthority {
    public Roles(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Id
    private Long id;
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "rolesList", fetch = FetchType.LAZY)
    private List<Users> users;

    @Override
    public String getAuthority() {
        return name;
    }

}
