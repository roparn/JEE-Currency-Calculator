package ee.roparn.currencycalculator.dao;

import ee.roparn.currencycalculator.handler.CurrencyTableXMLHandlerEE;
import ee.roparn.currencycalculator.model.CurrencyModel;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class EECurrencyXMLDAO extends CurrencyXMLDAO{

  private SAXParser saxParser;
  private final CurrencyTableXMLHandlerEE currencyTableXMLHandlerEE;

  public EECurrencyXMLDAO() {
    super();
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    saxParser = createSaxParser(saxParserFactory);
    currencyTableXMLHandlerEE = new CurrencyTableXMLHandlerEE();
  }

  @Override
  public List<CurrencyModel> saveAndParseCurrenciesXML(String currenciesXMLURL) throws Exception {
    try {
      createFileFromURL(currenciesXMLURL);

      return getCurrenciesFromSavedXML();
    } catch (Exception e){
      throw new Exception("Error downloading currencies");
    }
  }

  @Override
  public List<CurrencyModel> getCurrenciesFromSavedXML() throws IOException, SAXException {
    saxParser.parse(XML_FILE, currencyTableXMLHandlerEE);

    return currencyTableXMLHandlerEE.getCurrencies();
  }

  @Override
  public Date getDateFromSavedXML() throws IOException, SAXException {
    saxParser.parse(XML_FILE, currencyTableXMLHandlerEE);

    return currencyTableXMLHandlerEE.getCurrencyTableDate();
  }

  private SAXParser createSaxParser(SAXParserFactory saxParserFactory) {
    SAXParser saxParser = null;
    try {
      saxParser = saxParserFactory.newSAXParser();
    } catch (Exception e){
      e.printStackTrace();
    }
    return saxParser;
  }
}
