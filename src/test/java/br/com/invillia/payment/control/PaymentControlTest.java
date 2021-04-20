package br.com.invillia.payment.control;

import br.com.invillia.payment.domain.PaymentMapper;
import br.com.invillia.payment.domain.request.PaymentRequest;
import br.com.invillia.payment.domain.response.PaymentResponse;
import br.com.invillia.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentControlTest {

    private PaymentControl paymentControl;

    @Mock
    private PaymentService paymentService;

    private List<PaymentRequest> paymentRequests;

    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.openMocks(this);

        this.paymentControl = new PaymentControl(paymentService);

        this.paymentRequests = new ArrayList<>();

        paymentRequests.add(new PaymentRequest());
        paymentRequests.get(0).setName("Renan");
        paymentRequests.get(0).setValue(new BigDecimal("300"));
        paymentRequests.add(new PaymentRequest());
        paymentRequests.get(1).setName("Renan");
    }

    private void configureGet(String name, List<PaymentResponse> payment) {
        Mockito.when(paymentService.getPayment(name, 0, 5)).thenReturn(payment.stream()
                .filter((value -> value.getName().equals(name))).collect(Collectors.toList()));
    }

    private ResponseEntity<List<PaymentResponse>> configureGetAndResponseEntity(String name, List<PaymentRequest> payment) {
        List<PaymentResponse> responses = payment.stream().map(PaymentMapper.INSTANCE::paymentRequestToPaymentResponse).collect(Collectors.toList());
        configureGet(name, responses);
        return paymentControl.getPayment(name, 0, 5);
    }

    private void configurePost(PaymentRequest paymentRequest) {
        Optional<PaymentResponse> paymentResponse = Optional.empty();

        if(paymentRequest.verify()){
            paymentResponse = Optional.of(PaymentMapper.INSTANCE.paymentRequestToPaymentResponse(paymentRequest));
        }

        Mockito.when(paymentService.postPayment(paymentRequest)).thenReturn(paymentResponse);
    }

    private ResponseEntity<PaymentResponse> configurePostAndResponseEntity(PaymentRequest paymentRequest) {
        configurePost(paymentRequest);
        return paymentControl.postPayment(paymentRequest);
    }

    @Test
    void getWillReturnStatusCodeOK(){
        ResponseEntity<List<PaymentResponse>> responseEntity = configureGetAndResponseEntity("Renan", paymentRequests);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getWillReturnCorrectBody(){
        ResponseEntity<List<PaymentResponse>> responseEntity = configureGetAndResponseEntity("Renan", paymentRequests);
        List<PaymentResponse> paymentResponses = responseEntity.getBody();

        List<PaymentResponse> payment = paymentRequests.stream().map(PaymentMapper.INSTANCE::paymentRequestToPaymentResponse).collect(Collectors.toList());

        assertTrue(payment.equals(paymentResponses));
    }

    @Test
    void getWillReturnStatusCodeNotFound(){
        ResponseEntity<List<PaymentResponse>> responseEntity = configureGetAndResponseEntity("Scolari", paymentRequests);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getWillNotReturnCorrectBody(){
        ResponseEntity<List<PaymentResponse>> responseEntity = configureGetAndResponseEntity("Scolari", paymentRequests);

        List<PaymentResponse> paymentResponses = responseEntity.getBody();

        List<PaymentResponse> payment = paymentRequests.stream().map(PaymentMapper.INSTANCE::paymentRequestToPaymentResponse).collect(Collectors.toList());

        assertFalse(payment.equals(paymentResponses));
    }

    @Test
    void postWillReturnStatusCodeCreated(){
        ResponseEntity<PaymentResponse> responseEntity = configurePostAndResponseEntity(paymentRequests.get(0));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void postWillReturnCorrectBody(){
        ResponseEntity<PaymentResponse> responseEntity = configurePostAndResponseEntity(paymentRequests.get(0));

        PaymentResponse payment = PaymentMapper.INSTANCE.paymentRequestToPaymentResponse(paymentRequests.get(0));

        assertTrue(payment.equals(responseEntity.getBody()));
    }


    @Test
    void postWillReturnStatusCodeBadRequest(){
        ResponseEntity<PaymentResponse> responseEntity = configurePostAndResponseEntity(paymentRequests.get(1));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }


}
