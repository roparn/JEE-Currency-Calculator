package ee.roparn.currencycalculator.model;

public class CurrencyModel {
  private String name;
  private double rate;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }

  @Override
  public String toString() {
    return "CurrencyItem [name=" + name + ", rate="
            + rate + "]";
  }

}
