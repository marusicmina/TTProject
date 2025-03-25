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

   /*
    @GetMapping("/orders")
    public ResponseEntity<List<TradeOrderDTO>> getAllOrders() {
        List<TradeOrderDTO> orders = tradeOrderService.getAllOrders()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content ako nema naloga
        }

        return new ResponseEntity<>(orders, HttpStatus.OK); // 200 OK ako postoji barem jedan nalog
    }
*/  @GetMapping
public List<TradeOrderDTO> getAllOrders() {
    return tradeOrderService.getAllOrders()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    // ✅ Metoda za čuvanje novog naloga
    @PostMapping
    public TradeOrder createOrder(@RequestBody TradeOrder order) {
        return tradeOrderService.saveOrder(order);
    }

    // ✅ Metoda za dobijanje Top 10 Buy naloga (bez ID-a)
  /*  @GetMapping("/top-buy")
    public List<TradeOrderDTO> getTopBuyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return tradeOrderService.getTopBuyOrders(page, size)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Metoda za dobijanje Top 10 Sell naloga (bez ID-a)
    @GetMapping("/top-sell")
    public List<TradeOrderDTO> getTopSellOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return tradeOrderService.getTopSellOrders(page, size)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
*/
   
   /* // ✅ Metoda za konverziju u DTO (bez ID-a)
    private TradeOrderDTO convertToDTO(TradeOrder order) {
        return new TradeOrderDTO(
                order.getOrderType(),
                order.getPrice(),
                order.getAmount(),
                order.getCreatedAt(),
                order.getTrader().getUsername() // Samo username umesto celog Trader objekta
        );
    }*/
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