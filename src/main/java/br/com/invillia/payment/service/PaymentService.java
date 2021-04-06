package br.com.invillia.payment.service;

import br.com.invillia.payment.dto.PaymentDto;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    private PaymentClientService paymentClientService;

    private ProducerPaymentService producerPaymentService;

    @Autowired
    public PaymentService(PaymentClientService paymentClientService, ProducerPaymentService producerPaymentService) {
        this.paymentClientService = paymentClientService;
        this.producerPaymentService = producerPaymentService;
    }

    public Optional<PaymentDto> getPayment(String name) {
        return paymentClientService.getPayment(name);
    }

    public Optional<PaymentDto> postPayment(PaymentDto paymentDto) {
        producerPaymentService.postPayment(paymentDto);
        return Optional.empty();
    }
}
