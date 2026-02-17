package com.teksystems.gdit.demo.controller;

import com.teksystems.gdit.demo.model.request.ApplicationRequest;
import com.teksystems.gdit.demo.model.response.ApplicationResponse;
import com.teksystems.gdit.demo.service.ApplicationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ApplicationS Controller", description = "FAFSA Edit Rule Processor")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/application")
public class ApplicationsController {

  private final ApplicationsService applicationsService;

  @Operation(summary = "Applies validation rules (edits) to FAFSA application data.")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "Validations have successfully passed.",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationResponse.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", description = "No applications found for given information", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "502", description = "Forbidden", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(mediaType = "application/json"))
      })
  @PostMapping("/validate")
  public ResponseEntity<?> validateApplication(@RequestBody @Valid ApplicationRequest applicationRequest) {
    var response = applicationsService.validateApplication(applicationRequest);
    log.info("Validation response: {}", response);
    return ResponseEntity.ok(response);
  }
}
