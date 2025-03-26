package trader.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class TradeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private BigDecimal price;
    private Integer amount;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "trader_id", nullable = false)
    private Trader trader;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();  // Automatsko postavljanje vremena kreiranja
    }

    // Getteri i setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public OrderType getOrderType() { return orderType; }
    public void setOrderType(OrderType orderType) { this.orderType = orderType; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public Trader getTrader() { return trader; }
    public void setTrader(Trader trader) { this.trader = trader; }
}

