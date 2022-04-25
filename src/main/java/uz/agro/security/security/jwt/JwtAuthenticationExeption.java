package uz.agro.security.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationExeption extends AuthenticationException {

    public JwtAuthenticationExeption(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtAuthenticationExeption(String msg) {
        super(msg);
    }
}
