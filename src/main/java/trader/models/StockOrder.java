package trader.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StockOrder {  // Promeni naziv klase iz "Order" u "StockOrder"

    @Id
    private Long id;
    private String type; // "buy" ili "sell"
    private Double price;
    private Integer amount;

    // Getteri i setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
