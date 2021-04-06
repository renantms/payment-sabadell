package br.com.invillia.payment.service;

import br.com.invillia.payment.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerPaymentService {

    private KafkaTemplate<String, PaymentDto> kafkaTemplate;

    @Autowired
    public ProducerPaymentService(KafkaTemplate<String, PaymentDto> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean postPayment(PaymentDto paymentDto){
        if(!paymentDto.verify()){
            return false;
        }
        kafkaTemplate.send("payment3", paymentDto);
        return true;
    }

}
