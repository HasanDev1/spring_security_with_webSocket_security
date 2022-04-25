package uz.agro.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.agro.security.entity.Users;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    Users findByUsername(String username);
}
