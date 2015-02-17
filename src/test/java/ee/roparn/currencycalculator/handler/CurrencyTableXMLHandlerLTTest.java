package ee.roparn.currencycalculator.handler;

import ee.roparn.currencycalculator.model.CurrencyModel;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CurrencyTableXMLHandlerLTTest {

  private CurrencyTableXMLHandlerLT currencyTableXMLHandlerLT;

  @Before
  public void setup() throws ParserConfigurationException, SAXException, IOException {
    currencyTableXMLHandlerLT = new CurrencyTableXMLHandlerLT();
    SAXParserFactory.newInstance().newSAXParser().parse(this.getClass().getResourceAsStream("/lt-example-currencytable.xml"), currencyTableXMLHandlerLT);
  }

  @Test
  public void getCurrencies() {
    List<CurrencyModel> currencies = currencyTableXMLHandlerLT.getCurrencies();
    assertEquals(88, currencies.size());
  }
}
