package uz.agro.security.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseUsers {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

}
