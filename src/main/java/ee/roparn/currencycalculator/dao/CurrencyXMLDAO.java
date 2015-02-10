package ee.roparn.currencycalculator.dao;

import ee.roparn.currencycalculator.model.CurrencyModel;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public abstract class CurrencyXMLDAO {

  protected File XML_FILE;

  public CurrencyXMLDAO() {
    XML_FILE = new File(getFileNameWithDateFormat(new Date()));
  }

  public void createFileFromURLWithRequestedDate(String urlString, Date date) throws IOException {
    XML_FILE = new File(getFileNameWithDateFormat(date));
    createFileFromURL(String.format("%s?imported_at=%s", urlString, new SimpleDateFormat("dd.MM.yyyy").format(date)));
  }

  public void createFileFromURL(String urlString) throws IOException {
    openURLStreamAndWriteToFile(new URL(urlString));
  }

  public abstract List<CurrencyModel> saveAndParseCurrenciesXML(String currenciesXML) throws Exception;

  public abstract List<CurrencyModel> getCurrenciesFromSavedXML() throws IOException, SAXException;

  public abstract Date getDateFromSavedXML() throws IOException, SAXException;

  private void openURLStreamAndWriteToFile(URL url) throws IOException {

    try (Scanner scanner = new Scanner(url.openStream()); PrintWriter printWriter = new PrintWriter(XML_FILE)) {
      writeFromStream(scanner, printWriter);
    }
  }

  private void writeFromStream(Scanner scanner, PrintWriter out) {
    while (scanner.hasNext())
      out.println(scanner.next());
  }

  protected String getFileNameWithDateFormat(Date date) {
    return String.format("currencies_%s.xml", new SimpleDateFormat("dd-MM-yy").format(date));
  }
}
