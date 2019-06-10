package com.junit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class StockManagementTests {

  ExternalISBNDataService testWebService;
  ExternalISBNDataService testDatabaseService;
  StockManager stockmanager;

  @Before
  public void setup() {
    testWebService = mock(ExternalISBNDataService.class);
    testDatabaseService = mock(ExternalISBNDataService.class);
    stockmanager = new StockManager();
    stockmanager.setWebService(testWebService);
    stockmanager.setDatabaseService(testDatabaseService);
  }

  @Test
  public void getStockLocatorCode() {

    when(testWebService.lookup(anyString()))
        .thenReturn(new Book("0140177396", "Of Mice and Men", "J. Steinbeck"));

    //    ExternalISBNDataService testWebService =
    //        new ExternalISBNDataService() {
    //          @Override
    //          public Book lookup(String isbn) {
    //            return new Book(isbn, "Of Mice and Men", "J. Steinbeck");
    //          }
    //        };
    when(testDatabaseService.lookup(anyString())).thenReturn(null);
    //    ExternalISBNDataService testDatabaseService =
    //        new ExternalISBNDataService() {
    //          @Override
    //          public Book lookup(String isbn) {
    //            return null;
    //          }
    //        };
    String isbn = "0140177396";
    String locatorCode = stockmanager.getLocatorCode(isbn);
    assertEquals("7396J4", locatorCode);
  }

  @Test
  public void databaseIsUsedIfDataIsPresent() {

    when(testDatabaseService.lookup("0140177396"))
        .thenReturn(new Book("0140177396", "Mockito Book", "Mockito Author"));

    String isbn = "0140177396";
    String locatorCode = stockmanager.getLocatorCode(isbn);

    verify(testDatabaseService).lookup("0140177396");
    verify(testWebService, never()).lookup(anyString());
  }

  @Test
  public void webserviceIsUsedIfDataIsNotPresentInDatabase() {

    when(testDatabaseService.lookup("0140177396")).thenReturn(null);
    when(testWebService.lookup("0140177396"))
        .thenReturn(new Book("0140177396", "Mockito Book", "Mockito Author"));

    String isbn = "0140177396";
    String locatorCode = stockmanager.getLocatorCode(isbn);

    verify(testDatabaseService).lookup("0140177396");
    verify(testWebService).lookup("0140177396");
  }
}
