package br.com.invillia.payment.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class PaymentDto {

    private String name;

    private BigDecimal value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDto that = (PaymentDto) o;
        return name.equals(that.name) && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    public boolean verify() {
        return name != null && value != null && value.doubleValue() >= 0;
    }
}
