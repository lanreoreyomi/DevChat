package com.devchats.devchats.util;

import java.text.ParseException;
import java.time.LocalDate;

public class RegextUtils {

  //Should be validated on the frontEnd too. This utility class provides an additional layer of verification
  public static boolean dateIsValid(String date) {
    return date.matches("\\d{4}-\\d{2}-\\d{2}$");
  }

  public static boolean emailIsValid(String email) {
    return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
  }

  public static LocalDate parserDateOfBirth(String dtInString) throws ParseException {

    System.out.println("dtInString = " + dtInString);

    if (!dtInString.isEmpty()) {
      String day = dtInString.split("-")[2];
      String month = dtInString.split("-")[1];
      String year = dtInString.split("-")[0];
      return LocalDate.now()
          .of(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));

    } else {

      return null;

    }

  }
}
