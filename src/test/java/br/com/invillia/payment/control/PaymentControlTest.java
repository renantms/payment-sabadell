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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentControlTest {

    private PaymentControl paymentControl;

    @Mock
    private PaymentService paymentService;

    private List<PaymentDto> paymentDtoTest;

    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.openMocks(this);

        this.paymentControl = new PaymentControl(paymentService);

        this.paymentDtoTest = new ArrayList<>();

        paymentDtoTest.add(new PaymentDto());
        paymentDtoTest.get(0).setName("Renan");
        paymentDtoTest.get(0).setValue(new BigDecimal("300"));
    }

    private void configureGet(String requestParam, List<PaymentDto> paymentDto){
        Mockito.when(paymentService.getPayment(requestParam)).thenReturn(paymentDto);
    }

    private ResponseEntity<List<PaymentDto>> configureGetAndResponseEntity(String pathVariable, List<PaymentDto> paymentDto) {
        configureGet(pathVariable, paymentDto);
        return paymentControl.getPayment(pathVariable);
    }


//    private void configurePost(PaymentDto paymentDto, List<PaymentDto> optionalPaymentDto) {
//        Mockito.when(paymentService.postPayment(paymentDto)).thenReturn(optionalPaymentDto);
//    }
//
//    private ResponseEntity<PaymentDto> configurePostAndResponseEntity(PaymentDto paymentDto, Optional<PaymentDto> optionalPaymentDto) {
//        configurePost(paymentDto, optionalPaymentDto);
//        return paymentControl.postPayment(paymentDto);
//    }

    @Test
    void getWillReturnStatusCodeOK(){
        ResponseEntity<List<PaymentDto>> responseEntity = configureGetAndResponseEntity("Renan", paymentDtoTest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getWillReturnCorrectBody(){
        ResponseEntity<List<PaymentDto>> responseEntity = configureGetAndResponseEntity("Renan", paymentDtoTest);
        assertTrue(paymentDtoTest.equals(responseEntity.getBody()));
    }

    @Test
    void getWillReturnStatusCodeNotFound(){
        ResponseEntity<List<PaymentDto>> responseEntity = configureGetAndResponseEntity("Scolari", List.of());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getWillNotReturnCorrectBody(){
        ResponseEntity<List<PaymentDto>> responseEntity = configureGetAndResponseEntity("Scolari", List.of());
        assertFalse(paymentDtoTest.equals(responseEntity.getBody()));
    }

//    @Test
//    void postWillReturnStatusCodeCreated(){
//        ResponseEntity<PaymentDto> responseEntity = configurePostAndResponseEntity(paymentDtoTest, Optional.of(paymentDtoTest));
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//    }
//
//    @Test
//    void postWillReturnCorrectBody(){
//        ResponseEntity<PaymentDto> responseEntity = configurePostAndResponseEntity(paymentDtoTest, Optional.of(paymentDtoTest));
//        assertTrue(paymentDtoTest.equals(responseEntity.getBody()));
//    }
//
//
//    @Test
//    void postWillReturnStatusCodeBadRequest(){
//        ResponseEntity<PaymentDto> responseEntity = configurePostAndResponseEntity(paymentDtoTest, Optional.empty());
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//    }
//
//    @Test
//    void postWillNotReturnCorrectBody(){
//        ResponseEntity<PaymentDto> responseEntity = configurePostAndResponseEntity(paymentDtoTest, Optional.empty());
//        assertFalse(paymentDtoTest.equals(responseEntity.getBody()));
//    }

}
