package br.com.invillia.payment.domain.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentResponse {

    private String name;

    private BigDecimal value;

}
