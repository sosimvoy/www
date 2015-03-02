package com.etax.page;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;

public class PageXmlHandler extends DefaultHandler {

	private Map<String, PageInfoEntity> pageInfoMap;

	public PageXmlHandler(Map<String, PageInfoEntity> pageInfoMap) {
		this.pageInfoMap = pageInfoMap;
	}

	public void startElement(String uri,
							 String localName,
							 String qName,
							 Attributes attributes) throws SAXException  {

		if( attributes.getLength() != 0 ) {
			PageInfoEntity pageInfo = new PageInfoEntity();
			for( int i=0 ; i<attributes.getLength() ; i++ ) {

				if( "name".equals(attributes.getQName(i)) ) {
					pageInfo.setName(attributes.getValue(i));
				} else if( "login".equals(attributes.getQName(i)) ) {
					pageInfo.setLogin(attributes.getValue(i));
				}

				if( (pageInfo.getName() != null)&&(pageInfo.getLogin() != null) ) {
					pageInfoMap.put(pageInfo.getName(), pageInfo);
				}

			}
		}

	}

}