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

  protected File xmlFile;
  protected final Date requestedDate;

  public CurrencyXMLDAO(Date date) {
    requestedDate = date;
    xmlFile = new File(getFileNameWithDateFormat(date));
  }

  public void createFileFromURLWithRequestedDate(String urlString, Date date) throws IOException {
    xmlFile = new File(getFileNameWithDateFormat(date));
    createFileFromURL(String.format("%s?imported_at=%s", urlString, new SimpleDateFormat("dd.MM.yyyy").format(date)));
  }

  public void createFileFromURL(String urlString) throws IOException {
    openURLStreamAndWriteToFile(new URL(urlString));
  }

  public abstract List<CurrencyModel> saveAndParseCurrenciesXML(String currenciesXML) throws Exception;

  protected abstract List<CurrencyModel> getCurrenciesFromSavedXML() throws IOException, SAXException;

  public abstract Date getDateFromSavedXML() throws IOException, SAXException;

  public abstract List<CurrencyModel> getSavedOrDownloadCurrencies() throws Exception;

  private void openURLStreamAndWriteToFile(URL url) throws IOException {

    try (Scanner scanner = new Scanner(url.openStream()); PrintWriter printWriter = new PrintWriter(xmlFile)) {
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
