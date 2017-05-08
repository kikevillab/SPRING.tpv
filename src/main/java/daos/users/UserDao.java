package daos.users;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import entities.users.User;

public interface UserDao extends JpaRepository<User, Integer> {

    @Query("select token.user from Token token where token.value = ?1")
    public User findByTokenValue(String tokenValue);

    public User findByMobile(long mobile);

    public User findByUsername(String username);
    
    @Query("SELECT u FROM User u , Authorization a where a.role='CUSTOMER' and a.user_id=u.id ")
    public Page<User> findAllCustomer(Pageable pageable);
    
    @Query("SELECT u FROM User u , Authorization a where a.role='CUSTOMER' and a.user_id=u.id ")
    public List<User> findAllCustomerSin();
}
