package ee.roparn.currencycalculator.util;

import ee.roparn.currencycalculator.model.CurrencyModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ee.roparn.currencycalculator.util.Common.calculate;
import static ee.roparn.currencycalculator.util.Common.findCurrencyFromListByText;
import static org.junit.Assert.assertEquals;

public class CommonTest {

  @Test
  public void calculateTest(){

    assertEquals(calculate(createCurrency("AUD", 1.4123), createCurrency("USD", 1.1579), 19232), 15767, 1);

    assertEquals(calculate(createCurrency("EUR", 1.0), createCurrency("USD", 1.1579), 92123.12312), 106669, 1);

    assertEquals(calculate(createCurrency("SEK", 9.4297), createCurrency("MXN", 16.9308), 0.00123), 0.0022084354751476716, 0.00001);

    assertEquals(calculate(createCurrency("PHP", 51.6320), createCurrency("GBP", 0.7637), 0.5), 0.00739560737527115, 0.00001);

    assertEquals(calculate(createCurrency("PHP", 51.6320), createCurrency("GBP", 0.7637), 900), 13.3, 0.1);

    assertEquals(calculate(createCurrency("GBP", 0.7637), createCurrency("PHP", 51.6320), 900), 60846.9, 0.1);

    assertEquals(calculate(createCurrency("GBP", 0.7637), createCurrency("PHP", 51.6320), 0.25), 16.9, 0.1);

    assertEquals(calculate(createCurrency("CAMERON DIAZ", 0.00000042323), createCurrency("EUR", 1.0), 1.0), 2362781.46634, 0.1);

    assertEquals(calculate(createCurrency("EUR", 1.0), createCurrency("CAMERON DIAZ", 0.00000042323), 19191900.19), 8, 0.2);

  }

  @Test(expected = IllegalArgumentException.class)
  public void calculate_badCurrency1(){
    calculate(createCurrency("fake", -0.9), createCurrency("asd", 23), 12);
  }

  @Test(expected = IllegalArgumentException.class)
  public void calculate_badCurrency2(){
    calculate(createCurrency("fake", 112), createCurrency("asd", 0), 12);
  }

  @Test(expected = IllegalArgumentException.class)
  public void calculate_badCurrency3(){
    calculate(createCurrency("fake", 112), createCurrency("asd", 0.1), 0);
  }

  @Test
  public void findCurrencyFromListByTextTest() {
    List<CurrencyModel> currencies = createBunchOfCurrencies();
    CurrencyModel c = findCurrencyFromListByText("USD", currencies);

    assertEquals(c.getName(), "USD");
    assertEquals(c.getRate(), 100, 0.000001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void findCurrencyFromListByTextTest_notFound() {
    findCurrencyFromListByText("SEK", new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void findCurrencyFromListByTextTest_null() {
    findCurrencyFromListByText(null, createBunchOfCurrencies());
  }

  private List<CurrencyModel> createBunchOfCurrencies() {
    List<CurrencyModel> currencies = new ArrayList<>();
    currencies.add(createCurrency("asd", 12.3232));
    currencies.add(createCurrency("usd", 2));
    currencies.add(createCurrency("USD", 100));
    currencies.add(createCurrency("EUR", 3));
    currencies.add(createCurrency("wup", 4));

    return currencies;
  }

  private CurrencyModel createCurrency(String name, double rate){
    CurrencyModel currency = new CurrencyModel();
    currency.setName(name);
    currency.setRate(rate);

    return currency;
  }

}
