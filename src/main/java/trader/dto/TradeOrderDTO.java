package trader.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import trader.models.OrderType;
import trader.models.Trader;

public class TradeOrderDTO {

    private OrderType orderType;  
    private BigDecimal price;
    private Integer amount;
    private LocalDateTime createdAt;
    //private Trader trader;
    private String traderUsername; // Polje je sada tipa String umesto Trader


    // Konstruktor koji sadrži samo obeležja
    public TradeOrderDTO(OrderType orderType, BigDecimal price, Integer amount, LocalDateTime createdAt, String traderUsername) {
        this.orderType = orderType;
        this.price = price;
        this.amount = amount;
        this.createdAt = createdAt;
        this.traderUsername = traderUsername; // Sada se koristi samo username
    }
  
    @Override
    public String toString() {
        return "TradeOrderDTO{" +
                "orderType=" + orderType +
                ", price=" + price +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                ", traderUsername=" + traderUsername +
                '}';
    }
    
 // Getters i Setters
   

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getUsername() {
        return traderUsername;
    }

    public void setUsername(String traderUsername) {
        this.traderUsername = traderUsername;
    }



}
