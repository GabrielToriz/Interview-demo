package com.teksystems.gdit.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teksystems.gdit.demo.model.request.ApplicationRequest;
import com.teksystems.gdit.demo.model.response.ApplicationResponse;
import com.teksystems.gdit.demo.service.ApplicationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApplicationsController.class)
@ActiveProfiles({"unit-test"})
public class ApplicationsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ApplicationsService applicationsService;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Nested // Test-driven Development using AAA (Arrange-Act-Assert) pattern.
  class InputDataValidationTests {

    @Test /* 1. **Student Age**: Student must be at least 14 years old. */
    void validate_StudentMustBeAtLeast14Years() throws Exception {
      // Arrange
      ApplicationRequest request = getSuccessApplicationRequest();
      request.getStudentInfo().setDateOfBirth(
          LocalDate.now().minusYears(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

      mockMvc
          // Act
          .perform(
              post("/v1/application/validate")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request))
          )
          // Assert
          .andExpect(status().isBadRequest());
    }

    @Test /* 2. **SSN Format**: SSN must be in valid format (9 digits). */
    void validate_SSNFormat() throws Exception {
      // Arrange
      ApplicationRequest request = getSuccessApplicationRequest();
      request.getStudentInfo().setSsn("1234");

      mockMvc
          // Act
          .perform(
              post("/v1/application/validate")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request))
          )
          // Assert
          .andExpect(status().isBadRequest());
    }

    @Test /* 3. **Dependent Parent Income**: If dependency status is "dependent", parent income is required. */
    void validate_ParentInfoIfDependent() throws Exception {
      // Arrange
      ApplicationRequest request = getSuccessApplicationRequest();
      request.setDependencyStatus("dependent");
      request.getIncome().setParentIncome(null);

      mockMvc
          // Act
          .perform(
              post("/v1/application/validate")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request))
          )
          // Assert
          .andExpect(status().isBadRequest());
    }

    @Test /* 4. **Income Validation**: Income values cannot be negative. */
    void validate_IncomeValidation() throws Exception {
      // Arrange
      ApplicationRequest request = getSuccessApplicationRequest();
      request.getIncome().setParentIncome(-210000.00);
      request.getIncome().setStudentIncome(-10000.00);

      mockMvc
          // Act
          .perform(
              post("/v1/application/validate")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request))
          )
          // Assert
          .andExpect(status().isBadRequest());
    }

    @Test /* 5. **Household Logic**: Number in college cannot exceed number in household. */
    void validate_CollegeAndHouseholdNumbers() throws Exception {
      // Arrange
      ApplicationRequest request = getSuccessApplicationRequest();
      request.getHousehold().setNumberInCollege(5);
      request.getHousehold().setNumberInHousehold(3);

      mockMvc
          // Act
          .perform(
              post("/v1/application/validate")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request))
          )
          // Assert
          .andExpect(status().isBadRequest());
    }

    @Test /* 6. **State Code**: State code must be a valid US state abbreviation. */
    void validate_InvalidStateCode() throws Exception {
      // Arrange
      ApplicationRequest request = getSuccessApplicationRequest();
      request.setStateOfResidence("TT");

      mockMvc
          // Act
          .perform(
              post("/v1/application/validate")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request))
          )
          // Assert
          .andExpect(status().isBadRequest());
    }

    @Test /* 7. **Marital Status**: If marital status is "married", spouse information is required. */
    void validate_SpouseInfoIfMarried() throws Exception {
      // Arrange
      ApplicationRequest request = getSuccessApplicationRequest();
      request.setMaritalStatus("married");
      request.getSpouseInfo().setFirstName(null);

      mockMvc
          // Act
          .perform(
              post("/v1/application/validate")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request))
          )
          // Assert
          .andExpect(status().isBadRequest());
    }

    @Test
    void validate_Successful() throws Exception {
      // Arrange
      ApplicationRequest request = getSuccessApplicationRequest();

      ApplicationResponse expectedResponse = ApplicationResponse.builder()
          .applicationId(UUID.randomUUID().toString()).build();

      when(applicationsService.validateApplication(any())).thenReturn(expectedResponse);

      mockMvc
          // Act
          .perform(
              post("/v1/application/validate")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request))
          )
          // Assert
          .andExpect(status().isOk())
          .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    private ApplicationRequest getSuccessApplicationRequest() {
      return ApplicationRequest.builder()
          .studentInfo(ApplicationRequest.StudentInfo.builder()
              .firstName("Gabriel")
              .lastName("Toriz")
              .ssn("123456789")
              .dateOfBirth("2000-07-03")
              .build())
          .dependencyStatus("dependent")
          .maritalStatus("married")
          .household(ApplicationRequest.Household.builder()
              .numberInHousehold(4)
              .numberInCollege(2)
              .build())
          .income(ApplicationRequest.Income.builder()
              .studentIncome(15000.00)
              .parentIncome(200000.00)
              .build())
          .stateOfResidence("TX")
          .spouseInfo(ApplicationRequest.SpouseInfo.builder()
              .firstName("Jossiane")
              .lastName("Toriz")
              .ssn("123459999")
              .build())
          .build();
    }

  }
}
