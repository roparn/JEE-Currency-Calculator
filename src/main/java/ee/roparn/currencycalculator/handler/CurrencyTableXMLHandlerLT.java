package ee.roparn.currencycalculator.handler;

import ee.roparn.currencycalculator.model.CurrencyModel;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class CurrencyTableXMLHandlerLT extends DefaultHandler {

  private Stack<CurrencyModel> currencyStack = new Stack<>();
  private boolean currencyTagReached;
  private boolean quantityTagReached;
  private boolean rateTagReached;

  public List<CurrencyModel> getCurrencies() {
    return Arrays.asList(currencyStack.toArray(new CurrencyModel[currencyStack.size()]));
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (qName.equalsIgnoreCase("item")) {
      CurrencyModel currency = new CurrencyModel();
      currency.setRate(1);
      currencyStack.push(currency);
    }
    if (qName.equalsIgnoreCase("currency")) {
      currencyTagReached = true;
    }
    if (qName.equalsIgnoreCase("quantity")) {
      quantityTagReached = true;
    }
    if (qName.equalsIgnoreCase("rate")) {
      rateTagReached = true;
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (currencyTagReached) {
      currencyStack.peek().setName(new String(ch, start, length));
      currencyTagReached = false;
    }
    if (quantityTagReached) {
      CurrencyModel currency = currencyStack.peek();
      currency.setRate(Double.parseDouble(new String(ch, start, length)) / currency.getRate());
      quantityTagReached = false;
    }
    if (rateTagReached) {
      CurrencyModel currency = currencyStack.peek();
      currency.setRate(currency.getRate() * Double.parseDouble(new String(ch, start, length)));
      rateTagReached = false;
    }
  }
}
