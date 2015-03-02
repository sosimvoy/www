package com.etax.config;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;


public class ETaxConfig {

	private static Logger logger = Logger.getLogger(ETaxConfig.class);	// log4j ¼³Á¤

	private static Properties configuration;

	public Properties getConfiguration() {
		return ETaxConfig.configuration;
	}

	public static String getValue(String key) {
		if( configuration != null ) {
			return configuration.getProperty(key);
		} else {
			return "";
		}
	}

	public static String getString(String key) {
		return getValue(key);
	}

	public static int getInt(String key) {
		try {
			return Integer.parseInt(getString(key));
		} catch( Exception ex ) {
			ex.printStackTrace();
			return 0;
		}
	}

	public void load(String fileName) {
		logger.info("[ETaxConfig] configuration fileName : "+ fileName);
		InputStream fileInput = null;
		try {
			File file = new File(fileName);
			fileInput = new FileInputStream(file);
			if( fileInput != null ) {
				configuration = new Properties();
				configuration.load(fileInput);
				logger.info("[ETaxConfig] configuration loading is completed.");
			}
			
		} catch( FileNotFoundException ex ) {
			logger.error("[ETaxConfig] configuration load... FileNotFound Error");
			ex.printStackTrace();
		} catch( IOException ex ) {
			logger.error("[ETaxConfig] configuration load... IO Error");
			ex.printStackTrace();
		} finally {
			if( fileInput != null ) {
				try {
					fileInput.close();
				} catch( IOException e ) {
					logger.error("[ETaxConfig] configuration close Error");
					e.printStackTrace();
				}
			}
		}

	}
}
