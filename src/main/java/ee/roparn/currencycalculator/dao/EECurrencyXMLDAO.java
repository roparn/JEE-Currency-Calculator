package ee.roparn.currencycalculator.dao;

import ee.roparn.currencycalculator.handler.CurrencyTableXMLHandlerEE;
import ee.roparn.currencycalculator.model.CurrencyModel;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static ee.roparn.currencycalculator.util.Configuration.getConfiguration;

public class EECurrencyXMLDAO extends CurrencyXMLDAO {

  private final CurrencyTableXMLHandlerEE currencyTableXMLHandlerEE;

  public EECurrencyXMLDAO(Date date) throws ParserConfigurationException, SAXException {
    super(date);

    currencyTableXMLHandlerEE = new CurrencyTableXMLHandlerEE();
  }

  @Override
  public Date getDateFromSavedXML() throws IOException, SAXException {
    saxParser.parse(xmlFile, currencyTableXMLHandlerEE);

    return currencyTableXMLHandlerEE.getCurrencyTableDate();
  }

  @Override
  protected List<CurrencyModel> saveAndParseCurrenciesXML() throws Exception {
    String currenciesXMLURL = String.format("%s?imported_at=%s", getConfiguration().getEstonianBankCurrenciesXMLURL(), new SimpleDateFormat("dd.MM.yyyy").format(super.requestedDate));
    return super.saveAndParseCurrenciesXML(currenciesXMLURL);
  }

  @Override
  protected List<CurrencyModel> getCurrenciesFromSavedXML() throws IOException, SAXException {
    saxParser.parse(xmlFile, currencyTableXMLHandlerEE);

    return currencyTableXMLHandlerEE.getCurrencies();
  }

  @Override
  protected String getFileNameWithDateFormat(Date date) {
    return String.format("ee_currencies_%s.xml", new SimpleDateFormat("dd-MM-yy").format(date));
  }
}
