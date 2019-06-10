package com.junit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class StockManagementTests {

  @Test
  public void getStockLocatorCode() {

    ExternalISBNDataService testWebService =
        new ExternalISBNDataService() {
          @Override
          public Book lookup(String isbn) {
            return new Book(isbn, "Of Mice and Men", "J. Steinbeck");
          }
        };

    ExternalISBNDataService testDatabaseService =
        new ExternalISBNDataService() {
          @Override
          public Book lookup(String isbn) {
            return null;
          }
        };

    StockManager stockmanager = new StockManager();
    stockmanager.setWebService(testWebService);
    stockmanager.setDatabaseService(testDatabaseService);

    String isbn = "0140177396";
    String locatorCode = stockmanager.getLocatorCode(isbn);
    assertEquals("7396J4", locatorCode);
  }

  @Test
  public void databaseIsUsedIfDataIsPresent() {
    ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
    ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

    when(databaseService.lookup("0140177396"))
        .thenReturn(new Book("0140177396", "Mockito Book", "Mockito Author"));

    StockManager stockmanager = new StockManager();
    stockmanager.setWebService(webService);
    stockmanager.setDatabaseService(databaseService);

    String isbn = "0140177396";
    String locatorCode = stockmanager.getLocatorCode(isbn);

    verify(databaseService, times(1)).lookup("0140177396");
    verify(webService, times(0)).lookup(anyString());
  }

  @Test
  public void webserviceIsUsedIfDataIsNotPresentInDatabase() {
    ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
    ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

    when(databaseService.lookup("0140177396")).thenReturn(null);
    when(webService.lookup("0140177396"))
        .thenReturn(new Book("0140177396", "Mockito Book", "Mockito Author"));

    StockManager stockmanager = new StockManager();
    stockmanager.setWebService(webService);
    stockmanager.setDatabaseService(databaseService);

    String isbn = "0140177396";
    String locatorCode = stockmanager.getLocatorCode(isbn);

    verify(databaseService, times(1)).lookup("0140177396");
    verify(webService, times(1)).lookup("0140177396");
  }
}
