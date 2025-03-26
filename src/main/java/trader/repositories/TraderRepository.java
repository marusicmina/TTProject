package trader.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import trader.models.Trader;

public interface TraderRepository extends JpaRepository<Trader, Long> {
	 Optional<Trader> findByUsername(String username);
	    boolean existsByUsername(String username);
}
