package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.Voucher;

public interface VoucherDao extends JpaRepository<Voucher, Integer> {

}
