package com.junit.test;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberValidatorTest {

  NumberValidator validator = new NumberValidator();

  @Test
  public void isPrime() {
    Integer numbers[] = {1, 23, 61, 79};
    for (int i = 0; i < numbers.length; i++) {
      assertEquals(true, validator.isPrime(numbers[i]));
    }
  }

  @Test
  public void isNotPrime() {
    Integer numbers[] = {15, 25, 60, 63, 207};
    for (int i = 0; i < numbers.length; i++) {
      assertEquals(false, validator.isPrime(numbers[i]));
    }
  }
}
