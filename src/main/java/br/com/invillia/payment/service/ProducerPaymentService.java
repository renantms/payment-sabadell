package br.com.invillia.payment.service;

import br.com.invillia.payment.domain.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerPaymentService {

    private KafkaTemplate<String, PaymentRequest> kafkaTemplate;

    @Autowired
    public ProducerPaymentService(KafkaTemplate<String, PaymentRequest> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean postPayment(PaymentRequest paymentRequest){
        if(!paymentRequest.verify()){
            return false;
        }
        kafkaTemplate.send("payment3", paymentRequest);
        return true;
    }

}
