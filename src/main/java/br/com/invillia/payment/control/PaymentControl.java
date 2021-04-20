package br.com.invillia.payment.control;

import br.com.invillia.payment.domain.request.PaymentRequest;
import br.com.invillia.payment.domain.response.PaymentResponse;
import br.com.invillia.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentControl {

    private PaymentService paymentService;

    @Autowired
    public PaymentControl(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getPayment(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false, defaultValue = "0") int page,
                                                            @RequestParam(required = false, defaultValue = "5") int size){
        List<PaymentResponse> paymentResponses = paymentService.getPayment(name, page, size);

        if(paymentResponses.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(paymentResponses);
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> postPayment(@RequestBody PaymentRequest paymentRequest){
        Optional<PaymentResponse> paymentResponse = paymentService.postPayment(paymentRequest);

        if(paymentResponse.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create(paymentResponse.get().getName())).body(paymentResponse.get());
    }

}
