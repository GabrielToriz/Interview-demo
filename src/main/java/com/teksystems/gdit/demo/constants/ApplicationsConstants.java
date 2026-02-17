package com.teksystems.gdit.demo.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationsConstants {

  // -------------- Application statuses --------------//
  public enum APPLICATION_STATUSES {
    PENDING,
    REJECTED,
    SUBMITTED,
    ACCEPTED
  }

  // -------------- Valid US state codes --------------//
  public static final Set<String> VALID_STATE_CODES =
      Set.of("AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA",
          "HI","ID","IL","IN","IA","KS","KY","LA","ME","MD",
          "MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ",
          "NM","NY","NC","ND","OH","OK","OR","PA","RI","SC",
          "SD","TN","TX","UT","VT","VA","WA","WI","WV","WY","DC");

  // -------------- Error messages --------------//
  // TODO: For locale purposes, we should implement message bundles (en-US, es-ES, etc.).
  public static final String BIRTH_DATE_ERROR_MESSAGE = "Invalid birth date format. Please use yyyy-MM-dd.";

}
