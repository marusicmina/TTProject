package trader.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import trader.models.TradeOrder;
import java.util.List;

public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {
    // Dodatne metode mogu biti definisane ovde, ako su potrebne (npr. za filtriranje)

    @Query("SELECT t FROM TradeOrder t WHERE t.orderType = 'BUY' ORDER BY t.price DESC")
    List<TradeOrder> findTop10BuyOrders(Pageable pageable);

    @Query("SELECT t FROM TradeOrder t WHERE t.orderType = 'SELL' ORDER BY t.price ASC")
    List<TradeOrder> findTop10SellOrders(Pageable pageable);
}
