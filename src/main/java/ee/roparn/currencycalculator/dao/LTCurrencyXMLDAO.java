package ee.roparn.currencycalculator.dao;

import ee.roparn.currencycalculator.handler.CurrencyTableXMLHandlerLT;
import ee.roparn.currencycalculator.model.CurrencyModel;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static ee.roparn.currencycalculator.util.Configuration.getConfiguration;

public class LTCurrencyXMLDAO extends CurrencyXMLDAO {

  private final CurrencyTableXMLHandlerLT currencyTableXMLHandlerLT;

  public LTCurrencyXMLDAO(Date date) throws ParserConfigurationException, SAXException {
    super(date);

    currencyTableXMLHandlerLT = new CurrencyTableXMLHandlerLT();
  }

  @Override
  public Date getDateFromSavedXML() throws IOException, SAXException {
    return null;
  }

  @Override
  protected List<CurrencyModel> saveAndParseCurrenciesXML() throws Exception {
    String currenciesXMLURL = getConfiguration().getLithuanianBankCurrenciesXMLURL() + new SimpleDateFormat("yyyy-MM-dd").format(super.requestedDate);

    return super.saveAndParseCurrenciesXML(currenciesXMLURL);
  }

  @Override
  protected List<CurrencyModel> getCurrenciesFromSavedXML() throws IOException, SAXException {
    saxParser.parse(xmlFile, currencyTableXMLHandlerLT);

    return currencyTableXMLHandlerLT.getCurrencies();
  }

  @Override
  protected String getFileNameWithDateFormat(Date date) {
    return String.format("lt_currencies_%s.xml", new SimpleDateFormat("dd-MM-yy").format(date));
  }
}
