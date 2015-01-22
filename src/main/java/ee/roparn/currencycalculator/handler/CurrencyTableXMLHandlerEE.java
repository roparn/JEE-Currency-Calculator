package ee.roparn.currencycalculator.handler;

import ee.roparn.currencycalculator.model.CurrencyModel;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class CurrencyTableXMLHandlerEE extends DefaultHandler {

  private final static String CUBE = "Cube";
  private final static String CURRENCY = "currency";
  private final static String RATE = "rate";

  private List<CurrencyModel> currencies = new ArrayList<>();

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (qName.equalsIgnoreCase(CUBE)) {
      createCurrencyFromAttributesAndAddToList(attributes.getValue(CURRENCY), attributes.getValue(RATE));
    }
  }

  public List<CurrencyModel> getCurrencies() {
    return currencies;
  }

  private void createCurrencyFromAttributesAndAddToList(String currency, String rate) {
    if (currency != null && rate != null) {
      CurrencyModel currencyModel = new CurrencyModel();
      currencyModel.setName(currency);
      currencyModel.setRate(Double.parseDouble(rate));

      currencies.add(currencyModel);
    }
  }
}
