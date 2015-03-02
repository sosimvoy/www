package com.etax.page;

import org.xml.sax.SAXException;
import org.apache.log4j.Logger;
import java.util.*;

import com.etax.framework.exception.ProcessException;

public class PageInfoManager {

	private static Logger logger = Logger.getLogger(PageInfoManager.class);	// log4j ¼³Á¤

	private static Map<String, PageInfoEntity> pageInfoMap = new HashMap<String, PageInfoEntity>();

	public PageInfoManager() {

	}

	public static PageInfoEntity getPageInfo(String pageName) {
		return (PageInfoEntity)pageInfoMap.get(pageName);
	}

	public static Map<String, PageInfoEntity> getPageInfoMap() {
		return pageInfoMap;
	}

	public void loadData(String xmlFileName) {
		try {
			pageInfoMap.clear();
			PageXmlParser xmlParser = new PageXmlParser();
			xmlParser.loadData(xmlFileName, pageInfoMap);
			logger.info(xmlFileName + " is loaded.");
		} catch (SAXException ex) {
			logger.error("XML SAX Parsing Error");
			throw new ProcessException("[In page_list.xml] XML SAX Parsing Error");
		} catch (Exception ex) {
			logger.error("XML SAX ETC Error");
			throw new ProcessException("XML SAX Error");
		}
	}




}