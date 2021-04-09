package br.com.invillia.payment.service;

import br.com.invillia.payment.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private PaymentClientService paymentClientService;

    private ProducerPaymentService producerPaymentService;

    @Autowired
    public PaymentService(PaymentClientService paymentClientService, ProducerPaymentService producerPaymentService) {
        this.paymentClientService = paymentClientService;
        this.producerPaymentService = producerPaymentService;
    }

    public List<PaymentDto> getPayment(String name, int page, int size) {
        return paymentClientService.getPayment(name, page, size);
    }

    public boolean postPayment(PaymentDto paymentDto) {
        return producerPaymentService.postPayment(paymentDto);
    }
}
