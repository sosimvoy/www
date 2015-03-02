package com.etax.helper;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;

public class MessageHelper {

	private static Logger logger = Logger.getLogger(MessageHelper.class);	// log4j ¼³Á¤

	private static Properties messageTable;

	
	public MessageHelper() {

	}

	public static String getMessage(String key) {
		String message = "";
		try {
			if( (key == null) || key.length() != 4 ) return "";
			message = new String(messageTable.getProperty(key).getBytes("8859_1"),"KSC5601");
			logger.info("[MessageHelper] getMessage()  message : "+message);
		} catch (Exception ex) {
			logger.error("[MessageHelper] getMessage() message decoding error");
			ex.printStackTrace();
			return "";
		}
		return message;
	}

	public void load(String fileName) {
		logger.info("[MessageHelper] message fileName : "+ fileName);
		InputStream fileInput = null;
		try {
			File file = new File(fileName);
			fileInput = new FileInputStream(file);
			if( fileInput != null ) {
				messageTable = new Properties();
				messageTable.load(fileInput);
				logger.info("[MessageHelper] message loading is completed.");
			}
			
		} catch( FileNotFoundException ex ) {
			logger.error("[MessageHelper] load... FileNotFound Error");
			ex.printStackTrace();
		} catch( IOException ex ) {
			logger.error("[MessageHelper] load... IO Error");
			ex.printStackTrace();
		} finally {
			if( fileInput != null ) {
				try {
					fileInput.close();
				} catch( IOException e ) {
					logger.error("[MessageHelper] close Error");
					e.printStackTrace();
				}
			}
		}

	}

}
