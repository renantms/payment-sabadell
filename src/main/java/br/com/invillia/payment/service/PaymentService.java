package br.com.invillia.payment.service;

import br.com.invillia.payment.domain.PaymentMapper;
import br.com.invillia.payment.domain.request.PaymentRequest;
import br.com.invillia.payment.domain.response.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<PaymentResponse> getPayment(String name, int page, int size) {
        return paymentClientService.getPayment(name, page, size);
    }

    public Optional<PaymentResponse> postPayment(PaymentRequest paymentRequest) {
        if(!producerPaymentService.postPayment(paymentRequest)){
            return Optional.empty();
        }
        return Optional.of(PaymentMapper.INSTANCE.paymentRequestToPaymentResponse(paymentRequest));

    }
}
