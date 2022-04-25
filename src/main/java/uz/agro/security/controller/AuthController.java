package uz.agro.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import uz.agro.security.entity.Users;
import uz.agro.security.payload.RequestLogin;
import uz.agro.security.payload.RequestRegister;
import uz.agro.security.repository.UserRepository;
import uz.agro.security.security.jwt.JwtTokenProvider;
import uz.agro.security.security.jwt.SecurityUtils;
import uz.agro.security.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600L)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, SecurityUtils securityUtils, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody RequestLogin login){
        try {
            String username = login.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, login.getPassword()));
            Users users = userService.findByUsername(username);
            if(users == null){
                throw new UsernameNotFoundException("User with username: "+username +" not found");
            }
            String token = jwtTokenProvider.createToken(username, users.getRolesList());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException e){
            throw new BadCredentialsException("Envalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RequestRegister requestRegister){
        return ResponseEntity.ok().body(userService.register(requestRegister));
    }

    @GetMapping("/me")
    public ResponseEntity getMe(){
        String username = securityUtils.getCurrentUser().orElseThrow(()->new RuntimeException("current user not found"));
        return ResponseEntity.ok(userRepository.findByUsername(username));
    }


}
