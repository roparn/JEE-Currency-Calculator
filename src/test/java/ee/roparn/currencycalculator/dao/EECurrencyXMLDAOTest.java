package ee.roparn.currencycalculator.dao;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class EECurrencyXMLDAOTest {

  @Test
  public void createFileFromURL() {
    CurrencyXMLDAO eeCurrencyXMLDAO = new EECurrencyXMLDAO();
    eeCurrencyXMLDAO.createFileFromURL("http://www.eestipank.ee/valuutakursid/export/xml/latest");

    assertTrue(new File("currencies.xml").exists());
  }

}
