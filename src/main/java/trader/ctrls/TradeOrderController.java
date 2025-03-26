package trader.ctrls;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import trader.dto.TradeOrderDTO;
import trader.service.TradeOrderService;

@RestController
@RequestMapping("/trade-orders")
public class TradeOrderController {

    private final TradeOrderService tradeOrderService;

    public TradeOrderController(TradeOrderService tradeOrderService) {
        this.tradeOrderService = tradeOrderService;
    }

    @PostMapping
    public ResponseEntity<TradeOrderDTO> createTradeOrder(@RequestBody TradeOrderDTO tradeOrderDTO) {
        return ResponseEntity.ok(tradeOrderService.createTradeOrder(tradeOrderDTO));
    }

    @GetMapping
    public ResponseEntity<List<TradeOrderDTO>> getAllTradeOrders() {
        return ResponseEntity.ok(tradeOrderService.getAllTradeOrders());
    }
    
   
    @GetMapping("/top10-buy")
    public ResponseEntity<List<TradeOrderDTO>> getTop10BuyOrders() {
        return ResponseEntity.ok(tradeOrderService.getTop10BuyOrdersDTO());
    }

    @GetMapping("/top10-sell")
    public ResponseEntity<List<TradeOrderDTO>> getTop10SellOrders() {
        return ResponseEntity.ok(tradeOrderService.getTop10SellOrdersDTO());
    }
}

