package trader.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import trader.models.OrderType;
import trader.models.TradeOrder;
import trader.models.Trader;

public class TraderDTO {

    private String firstName;
    private String lastName;

    @NotBlank(message = "Username is required")
    @Column(unique = true, nullable = false)
    private String username;

    public TraderDTO(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
}