package uz.agro.security.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.agro.security.entity.Roles;
import uz.agro.security.entity.Status;
import uz.agro.security.entity.Users;
import uz.agro.security.payload.RequestRegister;
import uz.agro.security.repository.RoleRepository;
import uz.agro.security.repository.UserRepository;
import uz.agro.security.service.UserService;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Users register(RequestRegister requestRegister) {
        Roles roles = roleRepository.findByName("ROLE_USER");
        List<Roles> rolesList = new ArrayList<>();
        rolesList.add(roles);

        Users users = new Users();
        users.setPassword(passwordEncoder().encode(requestRegister.getPassword()));
        users.setRolesList(rolesList);
        users.setEmail(requestRegister.getEmail());
        users.setFirstName(requestRegister.getFirstName());
        users.setLastName(requestRegister.getLastName());
        users.setUsername(requestRegister.getUsername());
        users.setStatus(Status.ACTIVE);
        log.info("User registrated in database - {} ");
        return userRepository.save(users);
    }

    @Override
    public List<Users> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Users findByUsername(String username) {
        Users users = new Users();
        return userRepository.findByUsername(username);
    }

    @Override
    public Users findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
