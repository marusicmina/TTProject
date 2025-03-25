package trader.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
//@Table(name = "trade_order")
public class TradeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tip naloga: BUY ili SELL
    @NotNull(message = "Order type is required.")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;  
    
    // Cena naloga (mora biti veća od 0)
    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero.")
    private BigDecimal price;

    // Količina za nalog
    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "1", message = "Amount must be at least 1.")
    private Integer amount;

    // Vreme kada je nalog postavljen
    private LocalDateTime createdAt;

    // Povezivanje sa trgovcem (trader), koji je postavio nalog
    @ManyToOne
    @JoinColumn(name = "trader_id", nullable = false)
    private Trader trader; // Povezivanje sa trgovcem koji je postavio nalog

    // Konstruktor bez parametara, za JPA
    public TradeOrder() {
        this.createdAt = LocalDateTime.now(); // Automatski setuje vreme kreiranja
    }

    // Getters i Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

  public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }
    
    @Override
    public String toString() {
        return "TradeOrder{" +
                "id=" + id +
                ", orderType=" + orderType +
                ", price=" + price +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
               // ", trader=" + trader +
                '}';
    }
}
