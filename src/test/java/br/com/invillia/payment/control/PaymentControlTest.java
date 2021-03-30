package br.com.invillia.payment.control;

import br.com.invillia.payment.dto.PaymentDto;
import br.com.invillia.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentControlTest {

    private PaymentControl paymentControl;

    @Mock
    private PaymentService paymentService;

    private PaymentDto paymentDtoTest;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);

        this.paymentControl = new PaymentControl(paymentService);

        this.paymentDtoTest = new PaymentDto();
        paymentDtoTest.setName("Renan");
        paymentDtoTest.setValue(new BigDecimal("300"));
    }

    private void configureGet(String pathVariable, PaymentDto paymentDto){
        Mockito.when(paymentService.getPayment(pathVariable)).thenReturn(paymentDto);
    }

    private ResponseEntity<PaymentDto> configureGetAndResponseEntity(String pathVariable, PaymentDto paymentDto) {
        configureGet(pathVariable, paymentDto);
        return paymentControl.getPayment(pathVariable);
    }

    @Test
    void willReturnStatusCodeOK(){
        ResponseEntity<PaymentDto> responseEntity = configureGetAndResponseEntity("Renan", paymentDtoTest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void willReturnCorrectBody(){
        ResponseEntity<PaymentDto> responseEntity = configureGetAndResponseEntity("Renan", paymentDtoTest);
        assertTrue(paymentDtoTest.equals(responseEntity.getBody()));
    }

    @Test
    void willNotReturnCorrectBody(){
        ResponseEntity<PaymentDto> responseEntity = configureGetAndResponseEntity("Scolari", new PaymentDto());
        assertFalse(paymentDtoTest.equals(responseEntity.getBody()));
    }
}
