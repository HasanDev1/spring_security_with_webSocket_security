package uz.agro.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.agro.security.entity.abstractEntity.AbstractEntity;
import uz.agro.security.entity.helper.UserHelper;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users extends AbstractEntity {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> rolesList;

    @ManyToMany
    @JsonIgnore(value = true)
    List<Users> friends;

}
