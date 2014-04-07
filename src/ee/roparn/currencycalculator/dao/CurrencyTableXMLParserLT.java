package ee.roparn.currencycalculator.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class CurrencyTableXMLParserLT extends CurrencyTableXMLParser implements
ICurrencyTableXMLParser {

	static final String NAME = "currency";
	static final String QUANTITY = "quantity";
	static final String RATE = "rate";
	static final String ITEM = "item";
	static final String URL_STRING = "http://webservices.lb.lt/ExchangeRates/ExchangeRates.asmx/getExchangeRatesByDate?Date=2010-12-30";

	public CurrencyTableXMLParserLT() {
		createFileFromURL(URL_STRING);
	}

	@Override
	public List<CurrencyModel> parseXMLToDAO() {
		List<CurrencyModel> items = new ArrayList<CurrencyModel>();
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			InputStream in = new FileInputStream(XMLFILE);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			CurrencyModel item = null;
			Double qty = 0.0;

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().getLocalPart() == (ITEM)) {
						item = new CurrencyModel();

					} // if startelement item
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals(NAME)) {
							event = eventReader.nextEvent();
							item.setName((event.asCharacters().getData()));
							continue;
						}
					}
					if (event.asStartElement().getName().getLocalPart().equals(QUANTITY)) {
						event = eventReader.nextEvent();
						try {
							qty = Double.parseDouble((event.asCharacters().getData()));
						}catch(Exception e){}
						continue;
					}
					if (event.asStartElement().getName().getLocalPart().equals(RATE)) {
						event = eventReader.nextEvent();
						try {
							Double rate = Double.parseDouble((event.asCharacters().getData()));
							item.setRate(rate*qty);
						}catch(Exception e){}
						continue;
					}
				}
				// If we reach the end of an item element, we add it to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == (ITEM)) {
						items.add(item);
					}
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public void printList() {
		for (CurrencyModel item : parseXMLToDAO())
			System.out.println(item.toString());
	}
}
