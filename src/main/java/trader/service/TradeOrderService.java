package trader.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import trader.dto.TradeOrderDTO;
import trader.models.TradeOrder;
import trader.models.Trader;
import trader.repositories.TradeOrderRepository;
import trader.repositories.TraderRepository;
import trader.webSockets.OrderWebSocketHandler;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Validated
@Service
@EnableAsync
public class TradeOrderService {

    private final TradeOrderRepository tradeOrderRepository;
    private final TraderRepository traderRepository;
    private final HttpServletRequest request;
    private final OrderWebSocketHandler webSocketHandler; 

    public TradeOrderService(TradeOrderRepository tradeOrderRepository, 
                             TraderRepository traderRepository, 
                             HttpServletRequest request,
                             OrderWebSocketHandler webSocketHandler) {
        this.tradeOrderRepository = tradeOrderRepository;
        this.traderRepository = traderRepository;
        this.request = request;
        this.webSocketHandler = webSocketHandler;
    }

    private TradeOrderDTO fromEntity(TradeOrder tradeOrder) {
        return new TradeOrderDTO(
            tradeOrder.getOrderType(),
            tradeOrder.getPrice(),
            tradeOrder.getAmount(),
            tradeOrder.getTrader() != null ? tradeOrder.getTrader().getUsername() : "N/A"
        );
    }

    
    @Async
    @Transactional
    public CompletableFuture<TradeOrderDTO> createTradeOrder(@Valid TradeOrderDTO tradeOrderDTO, String username) {
        // Validacija se izvršava pre ulaska u asinhronu nit
        validateTradeOrderDTO(tradeOrderDTO);

        return CompletableFuture.supplyAsync(() -> {
            Trader trader = traderRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Trader not found"));

            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setOrderType(tradeOrderDTO.getOrderType());
            tradeOrder.setPrice(tradeOrderDTO.getPrice());
            tradeOrder.setAmount(tradeOrderDTO.getAmount());
            tradeOrder.setTrader(trader);

            tradeOrder = tradeOrderRepository.save(tradeOrder);

            // Emituj ažuriranje preko WebSocket-a
            convertTopOrdersToJson().thenAccept(webSocketHandler::broadcastOrderUpdate);

            return fromEntity(tradeOrder);
        });
    }

    private void validateTradeOrderDTO(TradeOrderDTO tradeOrderDTO) {
        if (tradeOrderDTO.getPrice().compareTo(BigDecimal.ONE) < 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (tradeOrderDTO.getAmount() < 1) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }

    



    @Async
    public CompletableFuture<String> convertTopOrdersToJson() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                CompletableFuture<List<TradeOrderDTO>> buyOrdersFuture = getTop10BuyOrdersDTO();
                CompletableFuture<List<TradeOrderDTO>> sellOrdersFuture = getTop10SellOrdersDTO();

                List<TradeOrderDTO> topBuyOrders = buyOrdersFuture.join();
                List<TradeOrderDTO> topSellOrders = sellOrdersFuture.join();

                return objectMapper.writeValueAsString(new Object() {
                    public final List<TradeOrderDTO> buyOrders = topBuyOrders;
                    public final List<TradeOrderDTO> sellOrders = topSellOrders;
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "{}";
            }
        });
    }

    @Async
    public CompletableFuture<List<TradeOrderDTO>> getAllTradeOrders() {
        return CompletableFuture.supplyAsync(() ->
            tradeOrderRepository.findAll()
                .stream()
                .map(TradeOrderDTO::fromEntity)
                .collect(Collectors.toList())
        );
    }

    @Async
    public CompletableFuture<List<TradeOrderDTO>> getTop10BuyOrdersDTO() {
        return CompletableFuture.supplyAsync(() ->
            tradeOrderRepository.findTop10BuyOrders(PageRequest.of(0, 10))
                .stream()
                .map(TradeOrderDTO::fromEntity)
                .collect(Collectors.toList())
        );
    }

    @Async
    public CompletableFuture<List<TradeOrderDTO>> getTop10SellOrdersDTO() {
        return CompletableFuture.supplyAsync(() ->
            tradeOrderRepository.findTop10SellOrders(PageRequest.of(0, 10))
                .stream()
                .map(TradeOrderDTO::fromEntity)
                .collect(Collectors.toList())
        );
    }

    private String convertOrderToJson(TradeOrderDTO order) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}"; 
        }
    }
}