package uz.agro.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.agro.security.entity.Roles;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Roles, UUID> {
    Roles findByName(String name);
}
