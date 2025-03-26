package trader.ctrls;
import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import trader.dto.TradeOrderDTO;
import trader.models.TradeOrder;
import trader.service.TradeOrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trade-orders")
public class TradeOrderController {

    @Autowired
    private TradeOrderService tradeOrderService;

    @GetMapping
    public List<TradeOrderDTO> getAllOrders() {
        return tradeOrderService.getAllOrders()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public TradeOrder createOrder(@RequestBody TradeOrder order) {
        return tradeOrderService.saveOrder(order);
    }

    private TradeOrderDTO convertToDTO(TradeOrder order) {
        return new TradeOrderDTO(
                order.getOrderType(),
                order.getPrice(),
                order.getAmount(),
                order.getCreatedAt(),
                order.getTrader().getUsername() // Samo username umesto celog Trader objekta
        );
    }
}
