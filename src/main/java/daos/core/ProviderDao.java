package daos.core;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.core.Provider;

public interface ProviderDao extends JpaRepository<Provider, Integer> {

}
