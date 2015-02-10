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
  private CurrencyXMLDAO eeCurrencyXMLDAO = new EECurrencyXMLDAO();

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void createFileFromURL() throws IOException {
    eeCurrencyXMLDAO.createFileFromURL(urlString);

    assertFileExistsAndDelete(eeCurrencyXMLDAO.getFileNameWithDateFormat(new Date()));
  }

  @Test
  public void createFileFromURLWithRequestedDate() throws ParseException, IOException, SAXException {
    Date date = new SimpleDateFormat("dd.MM.yy").parse("12.12.14");
    eeCurrencyXMLDAO.createFileFromURLWithRequestedDate(urlString, date);

    assertEquals("12.12.14", new SimpleDateFormat("dd.MM.yy").format(eeCurrencyXMLDAO.getDateFromSavedXML()));
    assertFileExistsAndDelete("currencies_12-12-14.xml");
  }

  @Test
  public void getCurrenciesFromSavedXML() throws IOException, SAXException, URISyntaxException {
    eeCurrencyXMLDAO.XML_FILE = new File(getClass().getResource("/ee-example-currencytable.xml").toURI());
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
      List<CurrencyModel> currencyModels = eeCurrencyXMLDAO.saveAndParseCurrenciesXML("URLISBAD");
      fail("Exception not thrown!");
    } catch (Exception e) {
      assertEquals("Error downloading currencies", e.getMessage());
    }
  }

  private void assertFileExistsAndDelete(String expectedFileName) {
    assertTrue(new File(eeCurrencyXMLDAO.XML_FILE.getAbsolutePath()).exists());
    assertEquals(expectedFileName, eeCurrencyXMLDAO.XML_FILE.getName());

    eeCurrencyXMLDAO.XML_FILE.delete();
  }

}
