package trader.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderType {
    BUY, SELL;

    @JsonCreator
    public static OrderType fromString(String value) {
        return OrderType.valueOf(value.toUpperCase()); // Omogućava "buy", "sell"
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
