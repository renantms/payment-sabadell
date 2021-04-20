package br.com.invillia.payment.domain.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    private String name;

    private BigDecimal value;

    public boolean verify() {
        return name != null && value != null && value.doubleValue() >= 0;
    }
}
