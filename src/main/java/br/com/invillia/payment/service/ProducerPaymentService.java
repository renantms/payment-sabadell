package br.com.invillia.payment.service;

import br.com.invillia.payment.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class ProducerPaymentService {

    private KafkaTemplate<String, PaymentDto> kafkaTemplate;

    @Autowired
    public ProducerPaymentService(KafkaTemplate<String, PaymentDto> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void postPayment(PaymentDto paymentDto){
        kafkaTemplate.send("payment", paymentDto);
    }

}
