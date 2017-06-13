package daos.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import entities.users.Role;
import entities.users.User;

public interface UserDao extends JpaRepository<User, Integer> {

    @Query("select token.user from Token token where token.value = ?1")
    public User findByTokenValue(String tokenValue);

    public User findByMobile(long mobile);

    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findByDni(String dni);
    
    @Query("select t.user from Ticket t where t.reference = ?1")
    User findByTicketReference(String reference);

    @Query("SELECT u FROM User u , Authorization a where a.role=?1 and a.user=u.id  and u.mobile=?2")
    public User findByMobileAndRole(Role role, long mobile);

    @Query("SELECT u FROM User u , Authorization a where a.role=?1 and a.user=u.id  and u.dni=?2")
    public User findByDniAndRole(Role role, String dni);

    @Query("SELECT u FROM User u , Authorization a where a.role=?1 and a.user=u.id  and u.email=?2")
    public User findByEmailAndRole(Role role, String email);

    @Query("SELECT u FROM User u , Authorization a where a.role=?1 and a.user=u.id ")
    public Page<User> findAllAndRole(Pageable pageable, Role role);

}
