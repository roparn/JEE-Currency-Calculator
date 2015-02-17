package ee.roparn.currencycalculator.dao;

import ee.roparn.currencycalculator.model.CurrencyModel;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public abstract class CurrencyXMLDAO {

  protected File xmlFile;
  protected final Date requestedDate;
  protected SAXParser saxParser;

  public CurrencyXMLDAO(Date date) throws ParserConfigurationException, SAXException {
    requestedDate = date;
    xmlFile = new File(getFileNameWithDateFormat(date));
    saxParser = SAXParserFactory.newInstance().newSAXParser();
  }

  public void createFileFromURL(String urlString) throws IOException {
    openURLStreamAndWriteToFile(new URL(urlString));
  }

  public List<CurrencyModel> getSavedOrDownloadCurrencies() throws Exception {
    if (xmlFile.exists()) {
      return getCurrenciesFromSavedXML();
    }

    return saveAndParseCurrenciesXML();
  }

  public abstract Date getDateFromSavedXML() throws IOException, SAXException;

  protected abstract List<CurrencyModel> saveAndParseCurrenciesXML() throws Exception;

  protected abstract List<CurrencyModel> getCurrenciesFromSavedXML() throws IOException, SAXException;

  protected List<CurrencyModel> saveAndParseCurrenciesXML(String currenciesXMLURL) throws Exception {
    try {
      createFileFromURL(currenciesXMLURL);

      return getCurrenciesFromSavedXML();
    } catch (Exception e) {
      throw new Exception("Error downloading currencies");
    }
  }

  protected void openURLStreamAndWriteToFile(URL url) throws IOException {
    try (Scanner scanner = new Scanner(url.openStream()); PrintWriter printWriter = new PrintWriter(xmlFile)) {
      writeFromStream(scanner, printWriter);
    }
  }

  protected void writeFromStream(Scanner scanner, PrintWriter out) {
    while (scanner.hasNext())
      out.println(scanner.next());
  }

  protected abstract String getFileNameWithDateFormat(Date date);
}
