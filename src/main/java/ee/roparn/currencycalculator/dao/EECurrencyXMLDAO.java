package ee.roparn.currencycalculator.dao;

import ee.roparn.currencycalculator.handler.CurrencyTableXMLHandlerEE;
import ee.roparn.currencycalculator.model.CurrencyModel;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static ee.roparn.currencycalculator.util.Configuration.getConfiguration;

public class EECurrencyXMLDAO extends CurrencyXMLDAO {

  private SAXParser saxParser;
  private final CurrencyTableXMLHandlerEE currencyTableXMLHandlerEE;

  public EECurrencyXMLDAO(Date date) throws ParserConfigurationException, SAXException {
    super(date);

    saxParser = SAXParserFactory.newInstance().newSAXParser();

    currencyTableXMLHandlerEE = new CurrencyTableXMLHandlerEE();
  }

  @Override
  public List<CurrencyModel> saveAndParseCurrenciesXML(String currenciesXMLURL) throws Exception {
    try {
      createFileFromURL(currenciesXMLURL);

      return getCurrenciesFromSavedXML();
    } catch (Exception e) {
      throw new Exception("Error downloading currencies");
    }
  }

  @Override
  public List<CurrencyModel> getSavedOrDownloadCurrencies() throws Exception {
    if (xmlFile.exists()) {
      return getCurrenciesFromSavedXML();
    }
    // TODO: perhaps move the URL generation to util.Common or something
    String currenciesXMLURL = String.format("%s?imported_at=%s", getConfiguration().getEstonianBankCurrenciesXMLURL(), new SimpleDateFormat("dd.MM.yyyy").format(super.requestedDate));

    return saveAndParseCurrenciesXML(currenciesXMLURL);
  }

  @Override
  public Date getDateFromSavedXML() throws IOException, SAXException {
    saxParser.parse(xmlFile, currencyTableXMLHandlerEE);

    return currencyTableXMLHandlerEE.getCurrencyTableDate();
  }

  @Override
  protected List<CurrencyModel> getCurrenciesFromSavedXML() throws IOException, SAXException {
    saxParser.parse(xmlFile, currencyTableXMLHandlerEE);

    return currencyTableXMLHandlerEE.getCurrencies();
  }
}
