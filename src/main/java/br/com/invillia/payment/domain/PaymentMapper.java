package br.com.invillia.payment.domain;

import br.com.invillia.payment.domain.request.PaymentRequest;
import br.com.invillia.payment.domain.response.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper( PaymentMapper.class );

    @Mapping(source = "name", target = "name")
    @Mapping(source = "value", target = "value")
    PaymentRequest paymentResponseToPaymentRequest(PaymentResponse paymentResponse);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "value", target = "value")
    PaymentResponse paymentRequestToPaymentResponse(PaymentRequest paymentRequest);

}
