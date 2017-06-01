package daos.users;

import java.util.Date;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import entities.users.Token;
import entities.users.User;
@Transactional
public interface TokenDao extends JpaRepository<Token, Integer> {

    Token findByUser(User user);
    
    int deleteByCreationDateLessThan(Date ahoraMenosTiempoExpiracion);
}
