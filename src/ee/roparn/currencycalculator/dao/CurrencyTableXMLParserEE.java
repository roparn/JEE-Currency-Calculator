package ee.roparn.currencycalculator.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class CurrencyTableXMLParserEE extends CurrencyTableXMLParser implements ICurrencyTableXMLParser {

	static final String NAME = "name";
	static final String TEXT = "text";
	static final String RATE = "rate";
	static final String ITEM = "Currency";
	static final String URL_STRING = "http://statistika.eestipank.ee/Reports?type=curd&format=xml&date1=2010-12-30&lng=est&print=off";

	public CurrencyTableXMLParserEE() {
		createFileFromURL(URL_STRING);
	}

	@SuppressWarnings("unchecked")
	public List<CurrencyModel> parseXMLToDAO() {
		List<CurrencyModel> items = new ArrayList<CurrencyModel>();
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			InputStream in = new FileInputStream(XMLFILE);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			CurrencyModel item = null;

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					// If we have an item element, we create a new item
					if (startElement.getName().getLocalPart() == (ITEM)) {
						item = new CurrencyModel();
						// We read the attributes from this tag
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals(NAME)) {
								item.setName(attribute.getValue());
								continue;
							} else if (attribute.getName().toString()
									.equals(RATE)) {
								try {
									String str = attribute.getValue();
									str = str.replaceAll(",", ".");
									str = str.replaceAll(" ", "");
									item.setRate(Double.parseDouble(str));
								} catch (Exception e) {
									e.printStackTrace();
								}
								continue;
							} else if (attribute.getName().toString()
									.equals(TEXT)) {
								item.setText(attribute.getValue());
								continue;
							}
						} // while
						items.add(item);
					} // if startelement
				}
			} // main while loop
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return items;
	}

	public void printList() {
		for (CurrencyModel item : parseXMLToDAO())
			System.out.println(item.toString());
	}
}