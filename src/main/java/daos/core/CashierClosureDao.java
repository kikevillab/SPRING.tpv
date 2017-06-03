package daos.core;

import entities.core.CashierClosure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashierClosureDao extends JpaRepository<CashierClosure, Long> {
    CashierClosure findFirstByOrderByOpeningDateDesc();
}
