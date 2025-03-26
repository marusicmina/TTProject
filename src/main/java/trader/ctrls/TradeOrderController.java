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
/*
    @PostMapping
    public TradeOrderDTO createOrder(@RequestBody TradeOrderDTO order) {
        return tradeOrderService.saveOrder(order);
    }*/


       // GET metoda za vraćanje Top 10 Buy naloga
       @GetMapping("/top10buy")
       public List<TradeOrderDTO> getTop10BuyOrders() {
           return tradeOrderService.getTopBuyOrders()
                   .stream()
                   .map(this::convertToDTO)
                   .collect(Collectors.toList());
       }

       // GET metoda za vraćanje Top 10 Sell naloga
       @GetMapping("/top10sell")
       public List<TradeOrderDTO> getTop10SellOrders() {
           return tradeOrderService.getTopSellOrders()
                   .stream()
                   .map(this::convertToDTO)
                   .collect(Collectors.toList());
       }

       // Metoda za konverziju u DTO
       private TradeOrderDTO convertToDTO(TradeOrder order) {
           return new TradeOrderDTO(
                   order.getOrderType(),
                   order.getPrice(),
                   order.getAmount(),
                   order.getCreatedAt(),
                   order.getTrader().getUsername()
           );
       }
   }

