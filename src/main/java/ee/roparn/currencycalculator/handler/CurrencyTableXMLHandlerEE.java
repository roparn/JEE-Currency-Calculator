package ee.roparn.currencycalculator.handler;

import ee.roparn.currencycalculator.model.CurrencyModel;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrencyTableXMLHandlerEE extends DefaultHandler {

  private final static String CUBE = "Cube";
  private final static String CURRENCY = "currency";
  private final static String RATE = "rate";
  private final static String DATE = "time";

  private List<CurrencyModel> currencies = new ArrayList<>();
  private Date currencyTableDate;

  public CurrencyTableXMLHandlerEE() {
    CurrencyModel c = new CurrencyModel();
    c.setName("EUR");
    c.setRate(1.0);
    currencies.add(c);
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (qName.equalsIgnoreCase(CUBE)) {
      createCurrencyFromAttributesAndAddToList(attributes.getValue(CURRENCY), attributes.getValue(RATE));
      getDateFromCurrenciesXML(attributes.getValue(DATE));
    }
  }

  public List<CurrencyModel> getCurrencies() {
    return currencies;
  }

  public Date getCurrencyTableDate() {
    return currencyTableDate;
  }


  private void createCurrencyFromAttributesAndAddToList(String currency, String rate) {
    if (currency != null && rate != null) {
      CurrencyModel currencyModel = new CurrencyModel();
      currencyModel.setName(currency);
      currencyModel.setRate(Double.parseDouble(rate));

      currencies.add(currencyModel);
    }
  }

  private void getDateFromCurrenciesXML(String dateString) {
    if (dateString != null && dateString.length() > 0) {
      try {
        currencyTableDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
  }
}
