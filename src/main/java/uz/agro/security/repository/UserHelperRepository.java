package uz.agro.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.agro.security.entity.Users;
import uz.agro.security.entity.helper.UserHelper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface UserHelperRepository extends JpaRepository<UserHelper, UUID> {

}
