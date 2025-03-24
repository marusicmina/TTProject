package trader.repositories;

//import javax.persistence.criteria.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import trader.models.StockOrder;

public interface OrderRepository extends JpaRepository<StockOrder, Long> {
    // Dodatne metode mogu biti definisane ovde, ako su potrebne (npr. za filtriranje)
}

