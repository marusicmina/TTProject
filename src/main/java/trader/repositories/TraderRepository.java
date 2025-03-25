package trader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import trader.models.Trader;

public interface TraderRepository extends JpaRepository<Trader, Long> {
   
}
