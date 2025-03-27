package trader.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<ResponseEntity<TradeOrderDTO>> createTradeOrder(
            @RequestBody @Valid TradeOrderDTO tradeOrderDTO, 
            @RequestAttribute("username") String username) {
        return tradeOrderService.createTradeOrder(tradeOrderDTO, username)
                .thenApply(ResponseEntity::ok);
    }


    @GetMapping("/all")
    public CompletableFuture<ResponseEntity<List<TradeOrderDTO>>> getAllTradeOrders() {
        return tradeOrderService.getAllTradeOrders()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/top10-buy")
    public CompletableFuture<ResponseEntity<List<TradeOrderDTO>>> getTop10BuyOrders() {
        return tradeOrderService.getTop10BuyOrdersDTO()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/top10-sell")
    public CompletableFuture<ResponseEntity<List<TradeOrderDTO>>> getTop10SellOrders() {
        return tradeOrderService.getTop10SellOrdersDTO()
                .thenApply(ResponseEntity::ok);
    }
}
