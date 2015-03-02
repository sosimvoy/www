package com.etax.page;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import java.util.*;

public class PageXmlParser {

	SAXParserFactory  parserFactory;
	SAXParser parser;
	DefaultHandler dHandler;

	public PageXmlParser() {

	}

	public void loadData(String xmlFile, Map<String, PageInfoEntity> pageInfoMap)
									throws SAXException, Exception {
		parserFactory = SAXParserFactory.newInstance();
		parser = parserFactory.newSAXParser();
		dHandler = new PageXmlHandler(pageInfoMap);
		parser.parse(xmlFile, dHandler);
	}

}