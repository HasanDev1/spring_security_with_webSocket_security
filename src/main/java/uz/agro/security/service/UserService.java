package uz.agro.security.service;


import org.springframework.http.ResponseEntity;
import uz.agro.security.entity.Users;
import uz.agro.security.model.Result;
import uz.agro.security.payload.RequestRegister;
import uz.agro.security.payload.ResponseUsers;

import java.util.List;
import java.util.UUID;

public interface UserService {
    Users register(RequestRegister requestRegister);
    List<Users> getAll();
    Users findByUsername(String username);
    Users findById(UUID id);
    void deleteUser(UUID id);
    ResponseEntity<Result> beFriend(UUID i, UUID myFriendID);
    ResponseEntity<Users> searchByUsername(String username);
    ResponseEntity<List<ResponseUsers>> findMyFriends(UUID myId);

}
