package uz.agro.security.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.agro.security.entity.Roles;
import uz.agro.security.entity.Status;
import uz.agro.security.entity.Users;
import uz.agro.security.entity.helper.UserHelper;
import uz.agro.security.model.Result;
import uz.agro.security.payload.RequestRegister;
import uz.agro.security.payload.ResponseUsers;
import uz.agro.security.repository.RoleRepository;
import uz.agro.security.repository.UserHelperRepository;
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
    private final UserHelperRepository userHelperRepository;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserHelperRepository userHelperRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userHelperRepository = userHelperRepository;
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
        log.info("User registrated in database - {} ", requestRegister);
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

    @Override
    public ResponseEntity<Result> beFriend(UUID current, UUID myFriendID) {
        System.out.println("currentId: " + current);
        System.out.println("myFriendID: " + myFriendID);
        Boolean isHave = true;
        List<UUID> friendsIds = userRepository.findFriendsByUserId(current);
        for (int i = 0; i < friendsIds.size(); i++) {
            if (friendsIds.get(i).equals(myFriendID)) {
                isHave = false;
                break;
            }
        }
        if (isHave) {
            userRepository.beFriend(current, myFriendID);
            userRepository.beFriend(myFriendID, current);
            return ResponseEntity.ok(new Result("siz endi do`stsiz", true));
        } else {
            return new ResponseEntity<Result>(new Result("bunday foydalanuvchi bazada yo`q", false), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Users> searchByUsername(String username) {
        Users user = userRepository.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<ResponseUsers>> findMyFriends(UUID myId) {
        System.out.println("idddddd " + myId);
        List<UUID> myFriendsId = new ArrayList<>();
        List<Users> usersList = new ArrayList<>();

        ResponseUsers responseUsers = new ResponseUsers();
        List<ResponseUsers> responseUsers1 = new ArrayList<>();

        myFriendsId = userRepository.findFriendsByUserId(myId);
        List<ResponseUsers> friends = userRepository.friends(myFriendsId);

        if (friends.size() > 0) {
            System.out.println(friends);
            return ResponseEntity.ok(friends);

        } else {
            return new ResponseEntity(new Result("sizda do`stlar mabjud emas", false), HttpStatus.NOT_FOUND);
        }
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
