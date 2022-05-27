package uz.agro.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.agro.security.entity.Users;
import uz.agro.security.payload.ResponseUsers;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
//    @Query(value="select Cast(s.sub_scribes_id as varchar) from users u inner join users_sub_scribes s on u.id = s.users_id and u.id = ?1 ", nativeQuery = true)
//    List<UUID> friendUserIds(UUID id);

    @Query(value = "select Cast (f.friends_id as varchar) from users_friends f inner join users u on u.id = f.users_id and u.id = ?1 ", nativeQuery = true)
    List<UUID> findFriendsByUserId(UUID userId);

    @Query("select new uz.agro.security.payload.ResponseUsers(u.id, u.username, u.firstName, u.lastName, u.email) from Users u where u.id in(?1)")
    List<ResponseUsers> friends(List<UUID> id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into users_friends (users_id, friends_id) values(?1, ?2)", nativeQuery = true)
    public void beFriend(UUID current, UUID myFriend);

    Users findByUsername(String username);
    List<Users> findAllById(UUID id);
}
