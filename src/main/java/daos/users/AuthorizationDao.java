package daos.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import entities.users.Authorization;
import entities.users.Role;
import entities.users.User;

public interface AuthorizationDao extends JpaRepository<Authorization, Integer> {

    @Query("select authorization.role from Authorization authorization where authorization.user = ?1")
    List<Role> findRoleByUser(User user);
}
