package com.teksystems.gdit.demo.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalClient {

  @Value("${external.operation.api-key}")
  private String apiKey;

  // TODO: To implement for calling external dependencies (either RestTemplate or ReactWebClient).
  //  This is where the CircuitBreaker would be used.
}
