package ee.roparn.currencycalculator.dao;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class CurrencyTableXMLParser {

	protected static final File XMLFILE = new File("currencies.xml");

	public CurrencyTableXMLParser() {
		super();
	}

	public void createFileFromURL(String urlString) {
		try {
			// Create a URL for the desired page
			URL url = new URL(urlString);
			Scanner scanner = new Scanner(url.openStream());
			PrintWriter out = new PrintWriter(XMLFILE);
			while (scanner.hasNext())
				out.println(scanner.next());
			scanner.close();
			out.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}