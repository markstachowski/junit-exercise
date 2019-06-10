package com.junit.test;

import java.util.Objects;

public class StockManager {

  private ExternalISBNDataService webService;
  private ExternalISBNDataService databaseService;

  public void setDatabaseService(ExternalISBNDataService databaseService) {
    this.databaseService = databaseService;
  }

  public void setWebService(ExternalISBNDataService webService) {
    this.webService = webService;
  }

  public String getLocatorCode(String isbn) {
    Book book = databaseService.lookup(isbn);
    if (Objects.isNull(book)) {
      book = webService.lookup(isbn);
    }
    StringBuilder locator = new StringBuilder();
    locator.append(isbn.substring(isbn.length() - 4));
    locator.append(book.getAuthor().substring(0, 1));
    locator.append(book.getTitle().split(" ").length);
    return locator.toString();
  }
}
