package uz.agro.security.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRegister {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
}
