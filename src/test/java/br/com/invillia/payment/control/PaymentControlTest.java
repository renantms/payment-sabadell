package br.com.invillia.payment.control;

import br.com.invillia.payment.dto.PaymentDto;
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
import java.util.Optional;

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

    private void configureGet(String pathVariable, Optional<PaymentDto> paymentDto){
        Mockito.when(paymentService.getPayment(pathVariable)).thenReturn(paymentDto);
    }

    private ResponseEntity<PaymentDto> configureGetAndResponseEntity(String pathVariable, Optional<PaymentDto> paymentDto) {
        configureGet(pathVariable, paymentDto);
        return paymentControl.getPayment(pathVariable);
    }


    private void configurePost(PaymentDto paymentDto, Optional<PaymentDto> optionalPaymentDto) {
        Mockito.when(paymentService.postPayment(paymentDto)).thenReturn(optionalPaymentDto);
    }

    private ResponseEntity<PaymentDto> configurePostAndResponseEntity(PaymentDto paymentDto, Optional<PaymentDto> optionalPaymentDto) {
        configurePost(paymentDto, optionalPaymentDto);
        return paymentControl.postPayment(paymentDto);
    }

    @Test
    void getWillReturnStatusCodeOK(){
        ResponseEntity<PaymentDto> responseEntity = configureGetAndResponseEntity("Renan", Optional.of(paymentDtoTest));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getWillReturnCorrectBody(){
        ResponseEntity<PaymentDto> responseEntity = configureGetAndResponseEntity("Renan", Optional.of(paymentDtoTest));
        assertTrue(paymentDtoTest.equals(responseEntity.getBody()));
    }

    @Test
    void getWillReturnStatusCodeNotFound(){
        ResponseEntity<PaymentDto> responseEntity = configureGetAndResponseEntity("Scolari", Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getWillNotReturnCorrectBody(){
        ResponseEntity<PaymentDto> responseEntity = configureGetAndResponseEntity("Scolari", Optional.empty());
        assertFalse(paymentDtoTest.equals(responseEntity.getBody()));
    }

    @Test
    void postWillReturnStatusCodeCreated(){
        ResponseEntity<PaymentDto> responseEntity = configurePostAndResponseEntity(paymentDtoTest, Optional.of(paymentDtoTest));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void postWillReturnCorrectBody(){
        ResponseEntity<PaymentDto> responseEntity = configurePostAndResponseEntity(paymentDtoTest, Optional.of(paymentDtoTest));
        assertTrue(paymentDtoTest.equals(responseEntity.getBody()));
    }


    @Test
    void postWillReturnStatusCodeBadRequest(){
        ResponseEntity<PaymentDto> responseEntity = configurePostAndResponseEntity(paymentDtoTest, Optional.empty());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void postWillNotReturnCorrectBody(){
        ResponseEntity<PaymentDto> responseEntity = configurePostAndResponseEntity(paymentDtoTest, Optional.empty());
        assertFalse(paymentDtoTest.equals(responseEntity.getBody()));
    }

}
