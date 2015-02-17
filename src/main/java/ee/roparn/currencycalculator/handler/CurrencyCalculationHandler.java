package ee.roparn.currencycalculator.handler;

import ee.roparn.currencycalculator.dao.CurrencyXMLDAO;
import ee.roparn.currencycalculator.dao.EECurrencyXMLDAO;
import ee.roparn.currencycalculator.dao.LTCurrencyXMLDAO;
import ee.roparn.currencycalculator.model.CurrencyModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Date;
import java.util.List;

public class CurrencyCalculationHandler {

  static final String EE_BANK = "Estonian Bank";
  static final String LT_BANK = "Lithuanian Bank";

  public JSONArray calculateResultFromAllSourcesAndParseToJSON(String inCurrency, String outCurrency, Date date, double amount) throws Exception {
    JSONArray jsonArray = new JSONArray();
    for (String s : new String[]{EE_BANK, LT_BANK}) {
      CurrencyXMLDAO dao = determineXMLDAOToUse(s, date);
      jsonArray.put(calculateResultAndParseToJSON(inCurrency, outCurrency, date, amount, dao, s));
    }
    return jsonArray;
  }

  protected JSONObject calculateResultAndParseToJSON(String inCurrency, String outCurrency, Date date, double amount, CurrencyXMLDAO currencyXMLDAO, String source) throws Exception {
    List<CurrencyModel> currencies = currencyXMLDAO.getSavedOrDownloadCurrencies();

    double result = findCurrenciesFromListAndCalculate(currencies, inCurrency, outCurrency, amount);

    return createResponseJSON(amount, inCurrency, outCurrency, result, date, source);
  }

  protected double calculate(CurrencyModel fromCurrency, CurrencyModel toCurrency, double amount) {
    if (fromCurrency.getRate() <= 0 || toCurrency.getRate() <= 0 || amount <= 0) {
      throw new IllegalArgumentException("Invalid currency rate or bad currency!");
    }
    return toCurrency.getRate() / fromCurrency.getRate() * amount;
  }

  protected CurrencyModel findCurrencyFromListByText(String desiredCurrency, List<CurrencyModel> currencies) {
    if (desiredCurrency == null)
      throw new IllegalArgumentException("String cannot be null");
    for (CurrencyModel c : currencies)
      if (c.getName().equals(desiredCurrency))
        return c;
    throw new IllegalArgumentException("Desired currency not found");
  }

  protected double findCurrenciesFromListAndCalculate(List<CurrencyModel> currencies, String inCurrency, String outCurrency, double amount) {
    CurrencyModel currencyFrom = findCurrencyFromListByText(inCurrency, currencies);
    CurrencyModel currencyTo = findCurrencyFromListByText(outCurrency, currencies);

    return calculate(currencyFrom, currencyTo, amount);
  }

  protected JSONObject createResponseJSON(double amount, String inCurrency, String outCurrency, double result, Date date, String source) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.accumulate("inCurrency", inCurrency);
    jsonObject.accumulate("outCurrency", outCurrency);
    jsonObject.accumulate("amount", amount);
    jsonObject.accumulate("result", result);
    jsonObject.accumulate("date", date);
    jsonObject.accumulate("source", source);

    return jsonObject;
  }

  protected CurrencyXMLDAO determineXMLDAOToUse(String currencyTableSource, Date date) throws ParserConfigurationException, SAXException {
    if (currencyTableSource.equals(EE_BANK))
      return new EECurrencyXMLDAO(date);
    if (currencyTableSource.equals(LT_BANK))
      return new LTCurrencyXMLDAO(date);

    return null;
  }
}
