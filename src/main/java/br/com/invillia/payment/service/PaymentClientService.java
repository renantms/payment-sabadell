package br.com.invillia.payment.service;

import br.com.invillia.payment.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@FeignClient(value = "payment", url = "localhost:8081/payment")
public interface PaymentClientService {

    @GetMapping
    List<PaymentDto> getPayment(@RequestParam String name);

}
