package ee.roparn.currencycalculator.handler;

import ee.roparn.currencycalculator.dao.EECurrencyXMLDAO;
import ee.roparn.currencycalculator.model.CurrencyModel;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class CurrencyCalculationHandler {

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

  public JSONObject calculateResultAndParseToJSON(String inCurrency, String outCurrency, Date date, double amount) throws Exception {
    List<CurrencyModel> currencies = new EECurrencyXMLDAO(date).getSavedOrDownloadCurrencies();

    double result = findCurrenciesFromListAndCalculate(currencies, inCurrency, outCurrency, amount);

    return createResponseJSON(amount, inCurrency, outCurrency, result, date);
  }

  protected JSONObject createResponseJSON(double amount, String inCurrency, String outCurrency, double result, Date date) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.accumulate("inCurrency", inCurrency);
    jsonObject.accumulate("outCurrency", outCurrency);
    jsonObject.accumulate("amount", amount);
    jsonObject.accumulate("result", result);
    jsonObject.accumulate("date", date);

    return jsonObject;
  }
}
