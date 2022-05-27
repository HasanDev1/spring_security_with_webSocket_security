package uz.agro.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.agro.security.entity.Users;
import uz.agro.security.payload.ResponseUsers;
import uz.agro.security.service.UserService;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600L)
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/fetchAllUsers")
    public List<Users> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping("/befriends/{currentId}/{friendId}")
    public ResponseEntity<?>getMyFriends(@PathVariable UUID currentId, @PathVariable UUID friendId){
        return userService.beFriend(currentId, friendId);
    }

    @GetMapping("/search")
    public ResponseEntity<Users> findByUsername(@RequestParam(defaultValue = "username") String username){
        return userService.searchByUsername(username);
    }
    @GetMapping("/get/myfriends/{userId}")
    public ResponseEntity<List<ResponseUsers>>findMyFriends(@PathVariable UUID userId ){
        return userService.findMyFriends(userId);
    }
}

