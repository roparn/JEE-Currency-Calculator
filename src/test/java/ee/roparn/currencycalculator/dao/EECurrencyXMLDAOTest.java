package ee.roparn.currencycalculator.dao;

import ee.roparn.currencycalculator.model.CurrencyModel;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EECurrencyXMLDAOTest {

  private final String urlString = "http://www.eestipank.ee/valuutakursid/export/xml/latest";
  private CurrencyXMLDAO eeCurrencyXMLDAO;

  @Before
  public void setUp() throws Exception {
    eeCurrencyXMLDAO = new EECurrencyXMLDAO(new Date());
  }

  @Test
  public void createFileFromURL() throws IOException {
    eeCurrencyXMLDAO.createFileFromURL(urlString);

    assertFileExistsAndDelete(eeCurrencyXMLDAO.getFileNameWithDateFormat(new Date()));
  }

  @Test
  public void getCurrenciesFromSavedXML() throws IOException, SAXException, URISyntaxException {
    eeCurrencyXMLDAO.xmlFile = new File(getClass().getResource("/ee-example-currencytable.xml").toURI());
    List<CurrencyModel> currencies = eeCurrencyXMLDAO.getCurrenciesFromSavedXML();
    assertEquals(currencies.size(), 31);
  }

  @Test
  public void saveAndParseCurrenciesXML_positiveFlow() throws Exception {
    List<CurrencyModel> currencyModels = eeCurrencyXMLDAO.saveAndParseCurrenciesXML(urlString);

    assertFileExistsAndDelete(eeCurrencyXMLDAO.getFileNameWithDateFormat(new Date()));
    assertEquals(31, currencyModels.size());
  }

  @Test
  public void saveAndParseCurrenciesXML_badURL() throws Exception {
    try {
      eeCurrencyXMLDAO.saveAndParseCurrenciesXML("URLISBAD");
      fail("Exception not thrown!");
    } catch (Exception e) {
      assertEquals("Error downloading currencies", e.getMessage());
    }
  }

  private void assertFileExistsAndDelete(String expectedFileName) {
    assertTrue(new File(eeCurrencyXMLDAO.xmlFile.getAbsolutePath()).exists());
    assertEquals(expectedFileName, eeCurrencyXMLDAO.xmlFile.getName());

    eeCurrencyXMLDAO.xmlFile.delete();
  }

}
