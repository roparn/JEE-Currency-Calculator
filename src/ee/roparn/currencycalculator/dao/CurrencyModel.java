package ee.roparn.currencycalculator.dao;

public class CurrencyModel {
	private String name;
	private String text;
	private double rate;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	@Override
	public String toString() {
		return "CurrencyItem [name=" + name + ", text=" + text + ", rate="
				+ rate + "]";
	}
	public double convertCurrency(CurrencyModel otherCurrency){
		return this.getRate()/otherCurrency.getRate();
	}
}
