package br.com.invillia.payment.service;

import br.com.invillia.payment.domain.PaymentMapper;
import br.com.invillia.payment.domain.request.PaymentRequest;
import br.com.invillia.payment.domain.response.PaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentServiceTest {

    private PaymentService paymentService;

    @Mock
    private PaymentClientService paymentClientService;

    @Mock
    private ProducerPaymentService producerPaymentService;

    private List<PaymentRequest> paymentRequests;

    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(paymentClientService, producerPaymentService);

        this.paymentRequests = new ArrayList<>();
        paymentRequests.add(new PaymentRequest());
        paymentRequests.get(0).setName("Renan");
        paymentRequests.get(0).setValue(new BigDecimal("300"));

        paymentRequests.add(new PaymentRequest());
        paymentRequests.get(1).setName("Renan");
    }

    private void configureGet(String name, List<PaymentResponse> payment) {
        Mockito.when(paymentClientService.getPayment(name, 0, 5)).thenReturn(payment.stream()
                .filter((value -> value.getName().equals(name))).collect(Collectors.toList()));
    }

    private List<PaymentResponse> configureGetAndReturn(String name, List<PaymentRequest> payment) {
        List<PaymentResponse> paymentResponses = payment.stream().map(PaymentMapper.INSTANCE::paymentRequestToPaymentResponse).collect(Collectors.toList());
        configureGet(name, paymentResponses);
        return paymentService.getPayment(name, 0, 5);
    }

    private void configurePost(PaymentRequest paymentRequest) {
        Mockito.when(producerPaymentService.postPayment(paymentRequest)).thenReturn(paymentRequest.verify());
    }

    private Optional<PaymentResponse> configurePostAndReturn(PaymentRequest paymentRequest) {
        configurePost(paymentRequest);
        return paymentService.postPayment(paymentRequest);
    }

    @Test
    void getWillFindPayment(){
        List<PaymentResponse> paymentResponses = configureGetAndReturn("Renan", paymentRequests);

        List<PaymentResponse> payment = paymentRequests.stream().map(PaymentMapper.INSTANCE::paymentRequestToPaymentResponse).collect(Collectors.toList());

        assertTrue(payment.equals(paymentResponses));
    }

    @Test
    void getWillNotFindPayment(){
        List<PaymentResponse> paymentResponses = configureGetAndReturn("Scolari", paymentRequests);
        assertTrue(paymentResponses.isEmpty());
    }

    @Test
    void postWillCreatePayment(){
        Optional<PaymentResponse> paymentResponse = configurePostAndReturn(paymentRequests.get(0));

        PaymentResponse payment = PaymentMapper.INSTANCE.paymentRequestToPaymentResponse(paymentRequests.get(0));

        assertTrue(paymentResponse.isPresent());
        assertEquals(payment, paymentResponse.get());
    }

    @Test
    void postWillNotCreatePayment(){
        Optional<PaymentResponse> paymentResponse = configurePostAndReturn(paymentRequests.get(1));
        assertFalse(paymentResponse.isPresent());
    }


}
