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

  public void createFileFromURL(String urlString) {
    try {
      URL url = new URL(urlString);
      openURLStreamAndWriteToFile(url);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public abstract List<CurrencyModel> saveAndParseCurrenciesXML();

  public abstract List<CurrencyModel> getCurrenciesFromSavedXML() throws IOException, SAXException;

  private void openURLStreamAndWriteToFile(URL url) throws IOException {
    Scanner scanner = new Scanner(url.openStream());
    PrintWriter printWriter = new PrintWriter(XMLFILE);

    try {
      writeFromStream(scanner, printWriter);
    } finally {
      scanner.close();
      printWriter.close();
    }
  }

  private void writeFromStream(Scanner scanner, PrintWriter out) {
    while (scanner.hasNext())
      out.println(scanner.next());
  }
}
