package br.com.invillia.payment.service;

import br.com.invillia.payment.dto.PaymentDto;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    private PaymentClientService paymentClientService;

    @Autowired
    public PaymentService(PaymentClientService paymentClientService) {
        this.paymentClientService = paymentClientService;
    }

    public Optional<PaymentDto> getPayment(String name) {
        return paymentClientService.getPayment(name);
    }

    public Optional<PaymentDto> postPayment(PaymentDto paymentDto) {
        try{
            return paymentClientService.postPayment(paymentDto);
        }catch(FeignException e){
            return Optional.empty();
        }

    }
}
