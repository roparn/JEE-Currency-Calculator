package ee.roparn.currencycalculator.handler;

import ee.roparn.currencycalculator.handler.CurrencyTableXMLHandlerEE;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CurrencyTableXMLHandlerEETest {

  private SAXParser saxParser;

  @Before
  public void setup() throws ParserConfigurationException, SAXException {
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    saxParser = saxParserFactory.newSAXParser();
  }

  @Test
  public void getCurrencies() throws ParserConfigurationException, SAXException, IOException {
    CurrencyTableXMLHandlerEE currencyTableXMLHandlerEE = new CurrencyTableXMLHandlerEE();
    saxParser.parse(this.getClass().getResourceAsStream("/ee-example-currencytable.xml"), currencyTableXMLHandlerEE);

    assertEquals(31, currencyTableXMLHandlerEE.getCurrencies().size());
  }
}
