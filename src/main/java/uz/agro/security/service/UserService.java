package uz.agro.security.service;


import uz.agro.security.entity.Users;
import uz.agro.security.payload.RequestRegister;

import java.util.List;
import java.util.UUID;

public interface UserService {
    Users register(RequestRegister requestRegister);
    List<Users> getAll();
    Users findByUsername(String username);
    Users findById(UUID id);
    void deleteUser(UUID id);

}
