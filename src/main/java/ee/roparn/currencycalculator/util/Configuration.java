package ee.roparn.currencycalculator.util;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

  Properties properties = new Properties();
  String propFileName = "/currencyapp.properties";
  static Configuration configuration;

  private Configuration() {
    try {
      properties.load(getClass().getResourceAsStream(propFileName));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void init() {
    configuration = new Configuration();
  }

  public static Configuration getConfiguration() {
    return configuration;
  }

  public String getEstonianBankCurrenciesXMLURL() {
    return properties.getProperty("EE_BANK_CURRENCIES_URL");
  }

  public String getMainPageJSPFileName() {
    return properties.getProperty("MAINPAGE");
  }

  public static void main(String[] args) {
    Configuration.init();
    String estonianBankCurrenciesXMLURL = Configuration.getConfiguration().getEstonianBankCurrenciesXMLURL();
    System.out.println();
  }
}
