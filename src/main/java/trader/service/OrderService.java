package trader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trader.models.StockOrder;
import trader.repositories.OrderRepository;
import websocket.OrderWebSocketHandler;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
//import javax.persistence.criteria.Order;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    
    public List<StockOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public StockOrder saveOrder(StockOrder order) {
        StockOrder savedOrder = orderRepository.save(order);

        // Kada se doda novi order, šaljemo poruku kroz WebSocket
        try {
            String orderJson = objectMapper.writeValueAsString(savedOrder);
            OrderWebSocketHandler.sendOrderUpdate(orderJson);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savedOrder;
    }


    // Dodatne metode po potrebi, kao što su filtriranje po ceni itd.
}
