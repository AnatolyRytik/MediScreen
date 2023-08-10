package com.mediscreen.assessment.config;

import com.mediscreen.assessment.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing Feign's error handling behavior.
 * <p>
 * This class provides a custom error decoder for handling specific HTTP response
 * status codes when using Feign clients. Specifically, it checks for HTTP 404
 * status codes and returns a custom {@link NotFoundException}.
 */
@Configuration
public class FeignErrorDecoderConfig {

    /**
     * Provides a custom Feign error decoder.
     * <p>
     * This error decoder checks the HTTP response status and returns appropriate
     * exceptions based on the status code. For a 404 status, it returns a
     * {@link NotFoundException}. For other unexpected statuses, it returns a
     * {@link RuntimeException}.
     *
     * @return An instance of {@link ErrorDecoder} configured with custom error handling logic.
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder() {
            @Override
            public Exception decode(String methodKey, Response response) {
                if (response.status() == 404) {
                    return new NotFoundException("Patient not found");
                }
                return new RuntimeException("Unexpected error occurred");
            }
        };
    }
}
