package com.mediscreen.assessment.config;

import com.mediscreen.assessment.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignErrorDecoderConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder() {
            @Override
            public Exception decode(String methodKey, Response response) {
                if(response.status() == 404){
                    return new NotFoundException("Patient not found");
                }
                return new RuntimeException("Unexpected error occurred");
            }
        };
    }
}
