package trader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trader.models.StockOrder;
import trader.repositories.OrderRepository;
import trader.webSockets.OrderWebSocketHandler;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderWebSocketHandler orderWebSocketHandler;  // Injektovanje WebSocket handlera

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Metoda za dobijanje svih naloga
    public List<StockOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    // Metoda za čuvanje novog naloga
    public StockOrder saveOrder(StockOrder order) {
        StockOrder savedOrder = orderRepository.save(order);

        // Kada se doda novi order, šaljemo poruku kroz WebSocket
        try {
            String orderJson = objectMapper.writeValueAsString(savedOrder);
            // Pozivamo WebSocket handler da pošalje ažurirani order svim klijentima
            orderWebSocketHandler.broadcastOrderUpdate(orderJson);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savedOrder;
    }

    // Dodatne metode po potrebi, kao što su filtriranje po ceni itd.
}

