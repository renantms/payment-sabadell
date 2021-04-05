package br.com.invillia.payment.control;

import br.com.invillia.payment.dto.PaymentDto;
import br.com.invillia.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentControl {

    private PaymentService paymentService;

    @Autowired
    public PaymentControl(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable String name){
        Optional<PaymentDto> paymentDto = paymentService.getPayment(name);

        if(paymentDto.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(paymentDto.get());
    }

    @PostMapping
    public ResponseEntity<PaymentDto> postPayment(@RequestBody PaymentDto paymentDto){
        Optional<PaymentDto> optionalPaymentDto = paymentService.postPayment(paymentDto);

        if(optionalPaymentDto.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(URI.create(optionalPaymentDto.get().getName())).body(optionalPaymentDto.get());
    }

}
