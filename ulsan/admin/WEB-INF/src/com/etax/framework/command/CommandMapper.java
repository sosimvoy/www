package com.etax.framework.command;

import org.xml.sax.SAXException;
import org.apache.log4j.Logger;

import com.etax.framework.xml.*;
import com.etax.framework.exception.ProcessException;

public class CommandMapper {

	private static Logger logger = Logger.getLogger(CommandMapper.class);	// log4j ¼³Á¤

	private String xmlFileName;
	
	public CommandMapper(String commandXmlFileName) {
		logger.info("CommandMapper is created, commandXmlFile=>"+commandXmlFileName);
		this.xmlFileName = commandXmlFileName;
	}

	public CommandConfiguration doMapping() {

		CommandConfiguration cmdConfig = new CommandConfiguration();
		try {
			CommandXmlParser xmlParser = new CommandXmlParser();
			xmlParser.saveConfiguration(this.xmlFileName, cmdConfig);
			return cmdConfig;
		} catch (SAXException ex) {
			logger.error("XML SAX Parsing Error");
			throw new ProcessException("[In cmdmaps directory] XML SAX Parsing Error");
		} catch (Exception ex) {
		    logger.error("XML SAX ETC Error" + "["+ex.toString()+"]");
			throw new ProcessException("XML SAX Error");
		}
	}

}