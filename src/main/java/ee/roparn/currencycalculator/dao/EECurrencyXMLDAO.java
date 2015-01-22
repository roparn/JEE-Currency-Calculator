package ee.roparn.currencycalculator.dao;

import ee.roparn.currencycalculator.handler.CurrencyTableXMLHandlerEE;
import ee.roparn.currencycalculator.model.CurrencyModel;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class EECurrencyXMLDAO extends CurrencyXMLDAO{

  private SAXParser saxParser;

  public EECurrencyXMLDAO() {
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    saxParser = createSaxParser(saxParserFactory);
  }

  @Override
  public List<CurrencyModel> saveAndParseCurrenciesXML(){
    List<CurrencyModel> currencies = null;
    try {
      createFileFromURL("https://www.eestipank.ee/valuutakursid/export/xml/latest");
      currencies = getCurrenciesFromSavedXML();
    } catch (Exception e){
      e.printStackTrace();
    }
    return currencies;
  }

  @Override
  public List<CurrencyModel> getCurrenciesFromSavedXML() throws IOException, SAXException {
    CurrencyTableXMLHandlerEE currencyTableXMLHandlerEE = new CurrencyTableXMLHandlerEE();
    saxParser.parse(XMLFILE, currencyTableXMLHandlerEE);

    return currencyTableXMLHandlerEE.getCurrencies();
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
