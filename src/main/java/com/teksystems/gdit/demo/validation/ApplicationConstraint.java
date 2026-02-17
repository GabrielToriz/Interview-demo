package com.teksystems.gdit.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ApplicationValidator.class)
public @interface ApplicationConstraint {
  String message() default "Invalid request. Please verify application data.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
