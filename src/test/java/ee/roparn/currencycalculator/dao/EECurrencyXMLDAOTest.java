package ee.roparn.currencycalculator.dao;

import ee.roparn.currencycalculator.model.CurrencyModel;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EECurrencyXMLDAOTest {

  private CurrencyXMLDAO eeCurrencyXMLDAO;

  @Before
  public void setUp() throws Exception {
    eeCurrencyXMLDAO = new EECurrencyXMLDAO();
  }

  @Test
  public void createFileFromURL() {
    eeCurrencyXMLDAO.createFileFromURL("http://www.eestipank.ee/valuutakursid/export/xml/latest");

    assertTrue(new File("currencies.xml").exists());
  }

  @Test
  public void getCurrenciesFromSavedXML() throws IOException, SAXException {
    List<CurrencyModel> currencies = eeCurrencyXMLDAO.getCurrenciesFromSavedXML();
    assertEquals(currencies.size(), 31);
  }

}
