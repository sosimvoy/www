package com.etax.framework.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.etax.framework.command.*;


public class CommandXmlHandler extends DefaultHandler {

	CommandConfiguration commandConfig;

	public CommandXmlHandler(CommandConfiguration commandConfig) {
		this.commandConfig = commandConfig;
	}

	public void startElement(String uri,
							 String localName,
							 String qName,
							 Attributes attributes)
				throws SAXException  {

		if( attributes.getLength() != 0 ) {
			CommandInfo cmdInfo = new CommandInfo();
			for( int i=0 ; i<attributes.getLength() ; i++ ) {

				if( "name".equals(attributes.getQName(i)) ) {
					cmdInfo.setName(attributes.getValue(i));
				} else if( "type".equals(attributes.getQName(i)) ) {
					cmdInfo.setType(attributes.getValue(i));
				} else if( "redirect".equals(attributes.getQName(i)) ) {
					cmdInfo.setRedirect(attributes.getValue(i));
				} else if( "desc".equals(attributes.getQName(i)) ) {
					cmdInfo.setDesc(attributes.getValue(i));
				}

				if( (cmdInfo.getName() != null)&&(cmdInfo.getType() != null) ) {
					commandConfig.addCommandInfo(cmdInfo);
				}

			}
		}

	}

}