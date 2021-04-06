package br.com.invillia.payment.service;

import br.com.invillia.payment.dto.PaymentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PaymentServiceTest {

    private PaymentService paymentService;

    @Mock
    private PaymentClientService paymentClientService;

    @Mock
    private ProducerPaymentService producerPaymentService;

    private PaymentDto paymentDtoTest;

    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(paymentClientService, producerPaymentService);

        paymentDtoTest = new PaymentDto();
        paymentDtoTest.setName("Renan");
        paymentDtoTest.setValue(new BigDecimal("300"));
    }

    private void configureGet(String name, Optional<PaymentDto> paymentDto){
        Mockito.when(paymentClientService.getPayment(name)).thenReturn(paymentDto);
    }

    private Optional<PaymentDto> configureGetAndReturn(String name, Optional<PaymentDto> paymentDtoTest) {
        configureGet(name, paymentDtoTest);
        return paymentService.getPayment(name);
    }

    private void configurePost(PaymentDto paymentDto, Optional<PaymentDto> paymentDtoOptional) {
        Mockito.when(paymentClientService.postPayment(paymentDto)).thenReturn(paymentDtoOptional);
    }

    private Optional<PaymentDto> configurePostAndReturn(PaymentDto paymentDto, Optional<PaymentDto> paymentDtoOptional) {
        configurePost(paymentDto, paymentDtoOptional);
        return paymentService.postPayment(paymentDto);
    }

    @Test
    void getWillFindPayment(){
        Optional<PaymentDto> paymentDto = configureGetAndReturn("Renan", Optional.of(paymentDtoTest));
        assertTrue(paymentDtoTest.equals(paymentDto.get()));
    }

    @Test
    void getWillNotFindPayment(){
        Optional<PaymentDto> paymentDto = configureGetAndReturn("Scolari", Optional.empty());
        assertFalse(paymentDto.isPresent());
    }

    @Test
    void postWillCreatePayment(){
        Optional<PaymentDto> paymentDto = configurePostAndReturn(paymentDtoTest, Optional.of(paymentDtoTest));
        assertTrue(paymentDtoTest.equals(paymentDto.get()));
    }

    @Test
    void postWillNotCreatePayment(){
        Optional<PaymentDto> paymentDto = configurePostAndReturn(paymentDtoTest, Optional.empty());
        assertFalse(paymentDto.isPresent());
    }


}
