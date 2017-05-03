package daos.users;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.users.Token;
import entities.users.User;

public interface TokenDao extends JpaRepository<Token, Integer> {

    Token findByUser(User user);
}
