package ee.roparn.currencycalculator.handler;

import ee.roparn.currencycalculator.handler.CurrencyTableXMLHandlerEE;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class CurrencyTableXMLHandlerEETest {

  private SAXParser saxParser;
  private CurrencyTableXMLHandlerEE currencyTableXMLHandlerEE;

  @Before
  public void setup() throws ParserConfigurationException, SAXException, IOException {
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    saxParser = saxParserFactory.newSAXParser();

    currencyTableXMLHandlerEE = new CurrencyTableXMLHandlerEE();
    saxParser.parse(this.getClass().getResourceAsStream("/ee-example-currencytable.xml"), currencyTableXMLHandlerEE);
  }

  @Test
  public void getCurrencies() {
    assertEquals(31, currencyTableXMLHandlerEE.getCurrencies().size());
  }

  @Test
  public void getCurrenciesTableDate() {
    assertEquals("21.01.2015", new SimpleDateFormat("dd.MM.yyyy").format(currencyTableXMLHandlerEE.getCurrencyTableDate()));
  }
}
