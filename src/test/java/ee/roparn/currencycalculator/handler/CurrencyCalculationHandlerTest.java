package ee.roparn.currencycalculator.handler;


import ee.roparn.currencycalculator.model.CurrencyModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CurrencyCalculationHandlerTest {

  CurrencyCalculationHandler calculationHandler;

  @Before
  public void setup() {
    calculationHandler = new CurrencyCalculationHandler();
  }

  @Test
  public void calculateTest(){

    assertEquals(15767, calculationHandler.calculate(createCurrency("AUD", 1.4123), createCurrency("USD", 1.1579), 19232), 1);

    assertEquals(106669, calculationHandler.calculate(createCurrency("EUR", 1.0), createCurrency("USD", 1.1579), 92123.12312), 1);

    assertEquals(0.0022084354751476716, calculationHandler.calculate(createCurrency("SEK", 9.4297), createCurrency("MXN", 16.9308), 0.00123), 0.00001);

    assertEquals(0.00739560737527115, calculationHandler.calculate(createCurrency("PHP", 51.6320), createCurrency("GBP", 0.7637), 0.5), 0.00001);

    assertEquals(13.3, calculationHandler.calculate(createCurrency("PHP", 51.6320), createCurrency("GBP", 0.7637), 900), 0.1);

    assertEquals(60846.9, calculationHandler.calculate(createCurrency("GBP", 0.7637), createCurrency("PHP", 51.6320), 900), 0.1);

    assertEquals(16.9, calculationHandler.calculate(createCurrency("GBP", 0.7637), createCurrency("PHP", 51.6320), 0.25), 0.1);

    assertEquals(2362781.46634, calculationHandler.calculate(createCurrency("CAMERON DIAZ", 0.00000042323), createCurrency("EUR", 1.0), 1.0), 0.1);

    assertEquals(8, calculationHandler.calculate(createCurrency("EUR", 1.0), createCurrency("CAMERON DIAZ", 0.00000042323), 19191900.19), 0.2);

  }

  @Test(expected = IllegalArgumentException.class)
  public void calculate_badCurrency1(){
    calculationHandler.calculate(createCurrency("fake", -0.9), createCurrency("asd", 23), 12);
  }

  @Test(expected = IllegalArgumentException.class)
  public void calculate_badCurrency2(){
    calculationHandler.calculate(createCurrency("fake", 112), createCurrency("asd", 0), 12);
  }

  @Test(expected = IllegalArgumentException.class)
  public void calculate_badCurrency3(){
    calculationHandler.calculate(createCurrency("fake", 112), createCurrency("asd", 0.1), 0);
  }

  @Test
  public void findCurrencyFromListByTextTest() {
    List<CurrencyModel> currencies = createBunchOfCurrencies();
    CurrencyModel c = calculationHandler.findCurrencyFromListByText("USD", currencies);

    assertEquals(c.getName(), "USD");
    assertEquals(c.getRate(), 100, 0.000001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void findCurrencyFromListByTextTest_notFound() {
    calculationHandler.findCurrencyFromListByText("SEK", new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void findCurrencyFromListByTextTest_null() {
    calculationHandler.findCurrencyFromListByText(null, createBunchOfCurrencies());
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
