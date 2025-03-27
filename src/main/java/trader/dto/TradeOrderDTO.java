package  trader.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import trader.models.OrderType;
import trader.models.TradeOrder;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;

public class TradeOrderDTO {

    @NotNull(message = "Order type is required")
    private OrderType orderType;

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Integer amount;

    @NotBlank(message = "Trader username cannot be blank")
    private String traderUsername;

    

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

   
    public OrderType getOrderType() { return orderType; }
    public void setOrderType(OrderType orderType) { this.orderType = orderType; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public String getTraderUsername() { return traderUsername; }
    public void setTraderUsername(String traderUsername) { this.traderUsername = traderUsername; }
}


