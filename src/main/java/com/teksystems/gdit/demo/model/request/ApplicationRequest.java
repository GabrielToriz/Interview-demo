package com.teksystems.gdit.demo.model.request;

import com.teksystems.gdit.demo.validation.ApplicationConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static com.teksystems.gdit.demo.constants.ApplicationsConstants.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApplicationConstraint
public class ApplicationRequest {

  @Valid
  private StudentInfo studentInfo;

  @NotBlank
  @Pattern(regexp = "^(dependent|independent)$", message = "Invalid value. Allowed values are 'dependent' and 'independent'.")
  private String dependencyStatus;

  @NotBlank
  @Pattern(regexp = "^(single|married)$", message = "Invalid value. Allowed values are 'single' and 'married'.")
  private String maritalStatus;

  @Valid
  private Household household;

  @Valid
  private Income income;

  @Valid
  private SpouseInfo spouseInfo;

  @NotBlank
  private String stateOfResidence;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class StudentInfo {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank(message = "SSN is required")
    @Pattern(
        regexp = "^(?!000|666|9\\d\\d)\\d{3}(?!00)\\d{2}(?!0000)\\d{4}$",
        message = "Invalid SSN format. Expected 9 digits with valid ranges."
    )
    private String ssn;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = BIRTH_DATE_ERROR_MESSAGE)
    private String dateOfBirth;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Household {
    @Min(value = 0)
    private Integer numberInHousehold;

    @Min(value = 0)
    private Integer numberInCollege;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Income {

    @DecimalMin(value = "0.00", message = "Field studentIncome must be greater than or equal to 0.00")
    private Double studentIncome;

    @DecimalMin(value = "0.00", message = "Field parentIncome must be greater than or equal to 0.00")
    private Double parentIncome;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SpouseInfo {
    private String firstName;
    private String lastName;
    private String ssn;
  }
}
