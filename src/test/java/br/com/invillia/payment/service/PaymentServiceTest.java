package br.com.invillia.payment.service;

import br.com.invillia.payment.dto.PaymentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PaymentServiceTest {

    private PaymentService paymentService;

    @Mock
    private PaymentClientService paymentClientService;

    @Mock
    private ProducerPaymentService producerPaymentService;

    private List<PaymentDto> paymentDtoTest;

    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(paymentClientService, producerPaymentService);

        this.paymentDtoTest = new ArrayList<>();
        paymentDtoTest.add(new PaymentDto());
        paymentDtoTest.get(0).setName("Renan");
        paymentDtoTest.get(0).setValue(new BigDecimal("300"));

        paymentDtoTest.add(new PaymentDto());
        paymentDtoTest.get(1).setName("Renan");
    }

    private void configureGet(String name, List<PaymentDto> payment) {
        Mockito.when(paymentClientService.getPayment(name)).thenReturn(payment.stream()
                .filter((value -> value.getName().equals(name))).collect(Collectors.toList()));
    }

    private List<PaymentDto> configureGetAndReturn(String name, List<PaymentDto> payment) {
        configureGet(name, payment);
        return paymentService.getPayment(name);
    }

    private void configurePost(PaymentDto paymentDto, boolean created) {
        Mockito.when(producerPaymentService.postPayment(paymentDto)).thenReturn(created);
    }

    private boolean configurePostAndReturn(PaymentDto paymentDto, boolean created) {
        configurePost(paymentDto, created);
        return paymentService.postPayment(paymentDto);
    }

    @Test
    void getWillFindPayment(){
        List<PaymentDto> paymentDto = configureGetAndReturn("Renan", paymentDtoTest);
        assertTrue(paymentDtoTest.equals(paymentDto));
    }

    @Test
    void getWillNotFindPayment(){
        List<PaymentDto> paymentDto = configureGetAndReturn("Scolari", paymentDtoTest);
        assertTrue(paymentDto.isEmpty());
    }

    @Test
    void postWillCreatePayment(){
        boolean created = configurePostAndReturn(paymentDtoTest.get(0), paymentDtoTest.get(0).verify());
        assertTrue(created);
    }

    @Test
    void postWillNotCreatePayment(){
        boolean created = configurePostAndReturn(paymentDtoTest.get(1), paymentDtoTest.get(1).verify());
        assertFalse(created);
    }


}
