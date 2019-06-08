package com.junit.test;

public class ValidateISBN {

  private static final int LONG_ISBN = 13;
  private static final int SHORT_ISBN = 10;
  private static final int LONG_ISBN_MULTIPLIER = 11;
  private static final int SHORT_ISBN_MULTIPLIER = 10;

  public boolean checkISBN(String isbn) {
    if (isbn.length() == LONG_ISBN) {
      return isValidLongISBN(isbn);
    } else if (isbn.length() == SHORT_ISBN) {
      return isValidShortISBN(isbn);
    }
    throw new NumberFormatException("Must contain 10 or 13 digits");
  }

  private boolean isValidShortISBN(String isbn) {
    int total = 0;
    for (int i = 0; i < SHORT_ISBN; i++) {
      if (!Character.isDigit(isbn.charAt(i))) {
        if (i == 9 && isbn.charAt(i) == 'X') {
          total += SHORT_ISBN;
        } else {
          throw new NumberFormatException("ISBN must be formatted with 9 digits and X");
        }
      } else {
        total += Character.getNumericValue(isbn.charAt(i)) * (SHORT_ISBN - i);
      }
    }
    return total % LONG_ISBN_MULTIPLIER == 0;
  }

  private boolean isValidLongISBN(String isbn) {
    int total = 0;
    for (int i = 0; i < LONG_ISBN; i++) {
      if (i % 2 == 0) {
        total += Character.getNumericValue(isbn.charAt(i));
      } else {
        total += Character.getNumericValue(isbn.charAt(i)) * 3;
      }
    }
    return total % SHORT_ISBN_MULTIPLIER == 0;
  }
}
