package com.junit.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidateISBNTest {

  @Test
  public void checkAValidISBN() {
    ValidateISBN validator = new ValidateISBN();
    boolean result = validator.checkISBN("0140449116");
    assertTrue("first test", result);
    result = validator.checkISBN("0140177396");
    assertTrue("second test", result);
  }

  @Test
  public void checkValidThirteenDigitISBN() {
    ValidateISBN validator = new ValidateISBN();
    boolean result = validator.checkISBN("9781501181009");
    assertTrue(result);
    result = validator.checkISBN("9780140446159");
    assertTrue(result);
  }

  @Test
  public void checkInvalidThirteenDigitISBN() {
    ValidateISBN validator = new ValidateISBN();
    boolean result = validator.checkISBN("9781501181008");
    assertFalse(result);
  }

  @Test
  public void ISBNNumbersEndingWithXAreValid() {
    ValidateISBN validator = new ValidateISBN();
    boolean result = validator.checkISBN("012000030X");
    assertTrue(result);
  }

  @Test
  public void checkAnInvalidISBN() {
    ValidateISBN validator = new ValidateISBN();
    boolean result = validator.checkISBN("0140449117");
    assertFalse(result);
  }

  @Test(expected = NumberFormatException.class)
  public void nineDigitISBNAreNotAllowed() {
    ValidateISBN validator = new ValidateISBN();
    validator.checkISBN("123456789");
  }

  @Test(expected = NumberFormatException.class)
  public void checkISBNIsAllDigits() {
    ValidateISBN validator = new ValidateISBN();
    validator.checkISBN("helloworld");
    validator.checkISBN("8miw8c9sj8");
  }
}
