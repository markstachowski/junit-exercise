package com.junit.test;

public class NumberValidator {

  public boolean isPrime(int number) {
    int maxDivisor = (int) Math.sqrt(number);
    for (int i = 2; i <= maxDivisor; i++) {
      if (number % i == 0) return false;
    }
    return true;
  }
}
