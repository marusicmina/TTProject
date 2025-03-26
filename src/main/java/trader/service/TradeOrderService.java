package trader.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trader.dto.TradeOrderDTO;
import trader.models.TradeOrder;
import trader.models.Trader;
import trader.repositories.TradeOrderRepository;
import trader.repositories.TraderRepository;

import javax.servlet.http.HttpServletRequest;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeOrderService {

    private final TradeOrderRepository tradeOrderRepository;
    private final TraderRepository traderRepository;
    private final HttpServletRequest request;

    public TradeOrderService(TradeOrderRepository tradeOrderRepository, 
                             TraderRepository traderRepository, 
                             HttpServletRequest request) {
        this.tradeOrderRepository = tradeOrderRepository;
        this.traderRepository = traderRepository;
        this.request = request;
    }

    @Transactional
    public TradeOrderDTO createTradeOrder(TradeOrderDTO tradeOrderDTO) {
        // Dobijamo username koji je postavljen u filteru
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

        return TradeOrderDTO.fromEntity(tradeOrder);
    }

    public List<TradeOrderDTO> getAllTradeOrders() {
        return tradeOrderRepository.findAll()
                .stream()
                .map(TradeOrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
   

    // Vraća top 10 BUY naloga po najvišoj ceni
    public List<TradeOrderDTO> getTop10BuyOrdersDTO() {
        return tradeOrderRepository.findTop10BuyOrders(PageRequest.of(0, 10))
                .stream()
                .map(TradeOrderDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Vraća top 10 SELL naloga po najnižoj ceni
    public List<TradeOrderDTO> getTop10SellOrdersDTO() {
        return tradeOrderRepository.findTop10SellOrders(PageRequest.of(0, 10))
                .stream()
                .map(TradeOrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
}



