package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.TextilePrinting;

public interface TextilePrintingDao extends JpaRepository<TextilePrinting, String> {

}
