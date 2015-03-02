package com.etax.framework.xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import com.etax.framework.command.CommandConfiguration;


public class CommandXmlParser {

	SAXParserFactory  parserFactory;
	SAXParser parser;
	DefaultHandler dHandler;

	public CommandXmlParser() {

	}

	public void saveConfiguration(String xmlFile, CommandConfiguration commandConfig)
									throws SAXException, Exception {
		parserFactory = SAXParserFactory.newInstance();
		parser = parserFactory.newSAXParser();
		dHandler = new CommandXmlHandler(commandConfig);
		parser.parse(xmlFile, dHandler);
	}

}