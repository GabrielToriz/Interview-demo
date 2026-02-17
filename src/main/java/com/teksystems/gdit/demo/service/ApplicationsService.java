package com.teksystems.gdit.demo.service;

import com.teksystems.gdit.demo.model.request.ApplicationRequest;
import com.teksystems.gdit.demo.model.response.ApplicationResponse;
import com.teksystems.gdit.demo.repository.ApplicationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;
import static com.teksystems.gdit.demo.constants.ApplicationsConstants.APPLICATION_STATUSES.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationsService {

  // Dependency injection by constructor (encouraged over setter injection)
  private final ApplicationsRepository applicationsRepository;

  public ApplicationResponse validateApplication(ApplicationRequest applicationRequest) {
    String id = UUID.randomUUID().toString();
    // TODO: Define persistence layer logic and/or external systems' layer.

    return ApplicationResponse.builder()
        .applicationId(id)
        .status(String.valueOf(PENDING))
        .build();
  }
}
