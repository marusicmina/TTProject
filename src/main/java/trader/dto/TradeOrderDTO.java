package trader.dto;

import java.math.BigDecimal;
import trader.models.OrderType;
import trader.models.TradeOrder;
import lombok.NoArgsConstructor;

@NoArgsConstructor  // Obezbeđuje prazan konstruktor za Jackson
public class TradeOrderDTO {

    private OrderType orderType;
    private BigDecimal price;
    private Integer amount;
    private String traderUsername; // Dodato korisničko ime tradera

    public TradeOrderDTO(OrderType orderType, BigDecimal price, Integer amount, String traderUsername) {
        this.orderType = orderType;
        this.price = price;
        this.amount = amount;
        this.traderUsername = traderUsername;
    }

    public static TradeOrderDTO fromEntity(TradeOrder tradeOrder) {
        return new TradeOrderDTO(
            tradeOrder.getOrderType(),
            tradeOrder.getPrice(),
            tradeOrder.getAmount(),
            tradeOrder.getTrader() != null ? tradeOrder.getTrader().getUsername() : "N/A" // Ako trader postoji
        );
    }

    // Getters i Setters
    public OrderType getOrderType() { return orderType; }
    public void setOrderType(OrderType orderType) { this.orderType = orderType; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public String getTraderUsername() { return traderUsername; }
    public void setTraderUsername(String traderUsername) { this.traderUsername = traderUsername; }
}


