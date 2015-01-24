package ee.roparn.currencycalculator.util;

import ee.roparn.currencycalculator.model.CurrencyModel;

import java.util.List;

public class Common {

  public static double calculate(CurrencyModel fromCurrency, CurrencyModel toCurrency, double amount) {
    if (fromCurrency.getRate() <= 0 || toCurrency.getRate() <= 0 || amount <= 0) {
      throw new IllegalArgumentException("Invalid currency rate or bad currency!");
    }
    return toCurrency.getRate() / fromCurrency.getRate() * amount;
  }

  public static CurrencyModel findCurrencyFromListByText(String desiredCurrency, List<CurrencyModel> currencies) {
    if (desiredCurrency == null)
      throw new IllegalArgumentException("String cannot be null");
    for (CurrencyModel c : currencies)
      if (c.getName().equals(desiredCurrency))
        return c;
    throw new IllegalArgumentException("Desired currency not found");
  }
}
