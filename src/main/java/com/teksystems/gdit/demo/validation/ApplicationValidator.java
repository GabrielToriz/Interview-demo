package com.teksystems.gdit.demo.validation;

import com.teksystems.gdit.demo.model.request.ApplicationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import static com.teksystems.gdit.demo.constants.ApplicationsConstants.VALID_STATE_CODES;

@Slf4j
public class ApplicationValidator implements ConstraintValidator<ApplicationConstraint, ApplicationRequest> {

  @Value("${applications.rules.minimum-age}")
  private String minimumAge;

  private void addConstraintViolation(ConstraintValidatorContext constraintValidatorContext, String field, String message) {
    String msg = StringUtils.isNotEmpty(message)? message : "Invalid value for field: '".concat(field.concat("'."));
    constraintValidatorContext.buildConstraintViolationWithTemplate(msg).addPropertyNode(field).addConstraintViolation();
    log.error(message);
  }

  @Override
  public boolean isValid(ApplicationRequest applicationRequest, ConstraintValidatorContext constraintValidatorContext) {

    if (ObjectUtils.isEmpty(applicationRequest.getStudentInfo()) ||
        ObjectUtils.isEmpty(applicationRequest.getIncome()) ||
        ObjectUtils.isEmpty(applicationRequest.getHousehold())
    ) {
      return false;
    }

    boolean isValid = true;

    try {
      /* 1. **Student Age**: Student must be at least 14 years old. */
      LocalDate dob = LocalDate.parse(applicationRequest.getStudentInfo().getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      if (Period.between(dob, LocalDate.now()).getYears() < Integer.parseInt(minimumAge)) {
        addConstraintViolation(constraintValidatorContext, "dateOfBirth", "Student must be at least 14 years old.");
        isValid = false;
      }

      /* 2. **SSN Format**: SSN must be in valid format (9 digits). */
      // This is being validated by REGEX expression pattern in request's data point using SpringBoot's validations.

      /* 3. **Dependent Parent Income**: If dependency status is "dependent", parent income is required. */
      if (StringUtils.equalsIgnoreCase(applicationRequest.getDependencyStatus(),"dependent")) {
        if (ObjectUtils.isEmpty(applicationRequest.getIncome().getParentIncome()) || applicationRequest.getIncome().getParentIncome() <= 0.00) {
          isValid = false;
          addConstraintViolation(constraintValidatorContext, "parentIncome", "Parent income is required for dependent applicants.");
        }
      }

      /* 4. **Income Validation**: Income values cannot be negative. */
      // This is being validated by Min-value checks in request's data point using SpringBoot's validations.

      /* 5. **Household Logic**: Number in college cannot exceed number in household. */
      if (applicationRequest.getHousehold().getNumberInCollege() > applicationRequest.getHousehold().getNumberInHousehold()) {
        addConstraintViolation(constraintValidatorContext, "household", "Number in college cannot exceed number in household.");
        isValid = false;
      }

      /* 6. **State Code**: State code must be a valid US state abbreviation. */
      if (!VALID_STATE_CODES.contains(applicationRequest.getStateOfResidence().trim().toUpperCase())) {
        addConstraintViolation(constraintValidatorContext, "stateOfResidence", null);
        isValid = false;
      }

      /* 7. **Marital Status**: If marital status is "married", spouse information is required. */
      if (StringUtils.equalsIgnoreCase(applicationRequest.getMaritalStatus(),"married")) {
        if (ObjectUtils.isEmpty(applicationRequest.getSpouseInfo()) || // Short-circuit to avoid NullPointerExceptions
            StringUtils.isBlank(applicationRequest.getSpouseInfo().getFirstName()) ||
            StringUtils.isBlank(applicationRequest.getSpouseInfo().getLastName()) ||
            StringUtils.isBlank(applicationRequest.getSpouseInfo().getSsn())
        ) {
          isValid = false;
          addConstraintViolation(constraintValidatorContext, "spouseInfo", "Spouse information is required for married applicants.");
        }
        if (!Pattern.matches("^(?!000|666|9\\d\\d)\\d{3}(?!00)\\d{2}(?!0000)\\d{4}$", applicationRequest.getSpouseInfo().getSsn())) {
          isValid = false;
          addConstraintViolation(constraintValidatorContext, "spouseInfo.ssn", "Spouse information is required for married applicants.");
        }
      }

    }
    catch (Exception e) {
      isValid = false;
    }

    return isValid;
  }
}
