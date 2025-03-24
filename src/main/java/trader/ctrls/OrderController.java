package trader.ctrls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import trader.models.StockOrder;
import trader.service.OrderService;

import java.util.List;

import javax.persistence.criteria.Order;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<StockOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public StockOrder createOrder(@RequestBody StockOrder order) {
        return orderService.saveOrder(order);
    }
}

