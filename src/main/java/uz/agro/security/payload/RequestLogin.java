package uz.agro.security.payload;

import lombok.Data;

@Data
public class RequestLogin {
    private String username;
    private String password;
}
