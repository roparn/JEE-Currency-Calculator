package ee.roparn.currencycalculator.dao;

import java.util.List;

public interface ICurrencyTableXMLParser {

	public abstract void createFileFromURL(String urlString);

	public abstract List<CurrencyModel> parseXMLToDAO();

	public abstract void printList();

}