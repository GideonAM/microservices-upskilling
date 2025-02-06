package com.redeemerlives.order_service.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        String errorResponse = getServiceResponse(response);

        if (methodKey.contains("findCustomerById"))
            throw new EntityNotFoundException(errorResponse);

        if (methodKey.contains("purchaseProducts"))
            throw new OperationNotPermitted(errorResponse);

        return new Exception();
    }

    private String getServiceResponse(Response response) {
        String errorResponse = "";

        try {
            InputStreamReader reader = new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8);
            StringBuilder builder = new StringBuilder();
            int character;

            while ((character = reader.read()) != -1)
                builder.append((char) character);

            errorResponse = builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return errorResponse;
    }
}
