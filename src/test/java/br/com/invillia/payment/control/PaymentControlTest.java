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
import java.util.stream.Collectors;

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
        paymentDtoTest.add(new PaymentDto());
        paymentDtoTest.get(1).setName("Renan");
    }

    private void configureGet(String name, List<PaymentDto> payment) {
        Mockito.when(paymentService.getPayment(name)).thenReturn(payment.stream()
                .filter((value -> value.getName().equals(name))).collect(Collectors.toList()));
    }

    private ResponseEntity<List<PaymentDto>> configureGetAndResponseEntity(String name, List<PaymentDto> payment) {
        configureGet(name, payment);
        return paymentControl.getPayment(name);
    }


    private void configurePost(PaymentDto paymentDto, boolean created) {
        Mockito.when(paymentService.postPayment(paymentDto)).thenReturn(created);
    }

    private ResponseEntity<PaymentDto> configurePostAndResponseEntity(PaymentDto paymentDto, boolean created) {
        configurePost(paymentDto, created);
        return paymentControl.postPayment(paymentDto);
    }

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
        ResponseEntity<List<PaymentDto>> responseEntity = configureGetAndResponseEntity("Scolari", paymentDtoTest);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getWillNotReturnCorrectBody(){
        ResponseEntity<List<PaymentDto>> responseEntity = configureGetAndResponseEntity("Scolari", paymentDtoTest);
        assertFalse(paymentDtoTest.equals(responseEntity.getBody()));
    }

    @Test
    void postWillReturnStatusCodeCreated(){
        ResponseEntity<PaymentDto> responseEntity = configurePostAndResponseEntity(paymentDtoTest.get(0), paymentDtoTest.get(0).verify());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void postWillReturnCorrectBody(){
        ResponseEntity<PaymentDto> responseEntity = configurePostAndResponseEntity(paymentDtoTest.get(0), paymentDtoTest.get(0).verify());
        assertTrue(paymentDtoTest.get(0).equals(responseEntity.getBody()));
    }


    @Test
    void postWillReturnStatusCodeBadRequest(){
        ResponseEntity<PaymentDto> responseEntity = configurePostAndResponseEntity(paymentDtoTest.get(1), paymentDtoTest.get(1).verify());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }


}
