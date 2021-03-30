package br.com.invillia.payment.service;

import br.com.invillia.payment.dto.PaymentDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    public Optional<PaymentDto> getPayment(String name) {
        return Optional.empty();
    }

    public Optional<PaymentDto> postPayment(PaymentDto paymentDto) {
        return Optional.empty();
    }
}
