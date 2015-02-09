package ee.roparn.currencycalculator.dao;

import ee.roparn.currencycalculator.model.CurrencyModel;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public abstract class CurrencyXMLDAO {

  protected static final File XMLFILE = new File("currencies.xml");

  public void createFileFromURL(String urlString) throws IOException {
    URL url = new URL(urlString);
    openURLStreamAndWriteToFile(url);
  }

  public abstract List<CurrencyModel> saveAndParseCurrenciesXML(String currenciesXML) throws Exception;

  public abstract List<CurrencyModel> getCurrenciesFromSavedXML() throws IOException, SAXException;

  private void openURLStreamAndWriteToFile(URL url) throws IOException {

    try (Scanner scanner = new Scanner(url.openStream()); PrintWriter printWriter = new PrintWriter(XMLFILE)) {
      writeFromStream(scanner, printWriter);
    }
  }

  private void writeFromStream(Scanner scanner, PrintWriter out) {
    while (scanner.hasNext())
      out.println(scanner.next());
  }
}
