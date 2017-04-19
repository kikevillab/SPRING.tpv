package daos.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import entities.users.User;

public interface UserDao extends JpaRepository<User, Integer> {

    @Query("select token.user from Token token where token.value = ?1")
    public User findByTokenValue(String tokenValue);

    public User findByMobile(long mobile);

    public User findByUsername(String username);
}
