package trader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trader.models.TradeOrder;
import trader.repositories.TradeOrderRepository;
import trader.webSockets.OrderWebSocketHandler;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TradeOrderService {

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    @Autowired
    private OrderWebSocketHandler orderWebSocketHandler;  // WebSocket handler za obaveštavanje klijenata

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Metoda za dobijanje svih naloga
    public List<TradeOrder> getAllOrders() {
        return tradeOrderRepository.findAll();
    }

    // Metoda za čuvanje novog naloga
    public TradeOrder saveOrder(TradeOrder order) {
        TradeOrder savedOrder = tradeOrderRepository.save(order);

        // Kada se doda novi order, šaljemo poruku kroz WebSocket
        try {
            String orderJson = objectMapper.writeValueAsString(savedOrder);
            orderWebSocketHandler.broadcastOrderUpdate(orderJson);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savedOrder;
    }

 // Metoda za dobijanje Top 10 Buy naloga
    public List<TradeOrder> getTopBuyOrders() {
        Pageable pageable = PageRequest.of(0, 10); // Prva stranica, 10 elemenata
        return tradeOrderRepository.findTop10BuyOrders(pageable);
    }

    // Metoda za dobijanje Top 10 Sell naloga
    public List<TradeOrder> getTopSellOrders() {
        Pageable pageable = PageRequest.of(0, 10); // Prva stranica, 10 elemenata
        return tradeOrderRepository.findTop10SellOrders(pageable);
    }

}

