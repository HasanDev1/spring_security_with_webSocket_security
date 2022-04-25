package uz.agro.security.loader;// Author - Orifjon Yunusjonov
// t.me/coderr24

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.agro.security.entity.Roles;
import uz.agro.security.entity.Status;
import uz.agro.security.entity.Users;
import uz.agro.security.repository.RoleRepository;
import uz.agro.security.repository.UserRepository;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String init;

    @Override
    public void run(String... args) throws Exception {

        try {
            if (init.equalsIgnoreCase("create")) {
                Roles roleUser = new Roles();
                roleUser.setId(1L);
                roleUser.setName("ROLE_USER");
                Roles roles = new Roles(2L, "ROLE_ADMIN");
                List<Roles> roleList = new ArrayList<>(Arrays.asList(roles, roleUser));

                Users users = new Users();
                users.setUsername("userTest");
                users.setPassword(passwordEncoder.encode("password"));
                users.setFirstName("Hasan");
                users.setLastName("Baxriddinov");
                users.setEmail("email@e.ru");
                users.setStatus(Status.ACTIVE);
                users.setRolesList(roleRepository.saveAll(roleList));
                userRepository.save(users);
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
        }
    }
}
