package trader.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trader.dto.TradeOrderDTO;
import trader.models.TradeOrder;
import trader.models.Trader;
import trader.repositories.TradeOrderRepository;
import trader.repositories.TraderRepository;
import trader.webSockets.OrderWebSocketHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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
    public TradeOrderDTO createTradeOrder(TradeOrderDTO tradeOrderDTO) {
        String username = (String) request.getAttribute("username");

        if (username == null) {
            throw new RuntimeException("User not authenticated");
        }

        Trader trader = traderRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trader not found"));

        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setOrderType(tradeOrderDTO.getOrderType());
        tradeOrder.setPrice(tradeOrderDTO.getPrice());
        tradeOrder.setAmount(tradeOrderDTO.getAmount());
        tradeOrder.setTrader(trader);

        tradeOrder = tradeOrderRepository.save(tradeOrder);

        // Emitujemo ažuriranje putem WebSocket-a
        String topOrdersJson = convertTopOrdersToJson();
        webSocketHandler.broadcastOrderUpdate(topOrdersJson);

        // Pozivamo fromEntity metodu da konvertujemo TradeOrder u TradeOrderDTO i vraćamo rezultat
        return fromEntity(tradeOrder);
    }


 
    public String convertTopOrdersToJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            List<TradeOrderDTO> topBuyOrders = getTop10BuyOrdersDTO();
            List<TradeOrderDTO> topSellOrders = getTop10SellOrdersDTO();

            
            String topOrdersJson = objectMapper.writeValueAsString(new Object() {
                public final List<TradeOrderDTO> buyOrders = topBuyOrders;
                public final List<TradeOrderDTO> sellOrders = topSellOrders;
            });

            return topOrdersJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}"; 
        }
    }

    @Async
    public List<TradeOrderDTO> getAllTradeOrders() {
        return tradeOrderRepository.findAll()
                .stream()
                .map(TradeOrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
    @Async
    public List<TradeOrderDTO> getTop10BuyOrdersDTO() {
        return tradeOrderRepository.findTop10BuyOrders(PageRequest.of(0, 10))
                .stream()
                .map(TradeOrderDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<TradeOrderDTO> getTop10SellOrdersDTO() {
        return tradeOrderRepository.findTop10SellOrders(PageRequest.of(0, 10))
                .stream()
                .map(TradeOrderDTO::fromEntity)
                .collect(Collectors.toList());
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



