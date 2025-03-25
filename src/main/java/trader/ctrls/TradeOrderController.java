package trader.ctrls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trader.models.TradeOrder;
import trader.service.TradeOrderService;

import java.util.List;

@RestController
@RequestMapping("/trade-orders")
public class TradeOrderController {

    @Autowired
    private TradeOrderService tradeOrderService;

    // Metoda za dobijanje svih naloga
    @GetMapping
    public List<TradeOrder> getAllOrders() {
        return tradeOrderService.getAllOrders();
    }

    // Metoda za čuvanje novog naloga
    @PostMapping
    public TradeOrder createOrder(@RequestBody TradeOrder order) {
        return tradeOrderService.saveOrder(order);
    }

    // Metoda za dobijanje Top 10 Buy naloga
    @GetMapping("/top-buy")
    public List<TradeOrder> getTopBuyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return tradeOrderService.getTopBuyOrders(page, size);
    }

    // Metoda za dobijanje Top 10 Sell naloga
    @GetMapping("/top-sell")
    public List<TradeOrder> getTopSellOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return tradeOrderService.getTopSellOrders(page, size);
    }
}
