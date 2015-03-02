package com.etax.util;

import javax.servlet.http.*;
import java.io.*; 
import org.apache.log4j.Logger;

import com.etax.config.ETaxConfig;
import com.etax.framework.exception.ProcessException;

public class FileDownloader {

	private static Logger logger = Logger.getLogger(FileDownloader.class);	// log4j ¼³Á¤

	private String fileName;
	private String dir;
	private int maxSize;

	public FileDownloader() {
		this.dir = ETaxConfig.getString("upload_dir");
		this.maxSize = ETaxConfig.getInt("upload_size") * 1024;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setDir(String dir) {
		this.dir += "/" + dir + "/";
	}

	public void download(HttpServletResponse res) {
		BufferedInputStream bif = null;
		BufferedOutputStream bof = null;
		

		try {
			String fullFileName = this.dir + this.fileName;
			logger.info("fullfileName=>"+ fullFileName);
			File file = new File(fullFileName);
			FileInputStream fis = new FileInputStream(file);
			res.reset();
			res.setHeader("Content-Type", "application/octet-stream");
			res.setHeader("Content-Disposition", "attachment;filename="+this.fileName);
			res.setHeader("Content-Transfer-Encoding", "Binary");
            res.setHeader("Pragma", "no-cache");
            res.setHeader("Expires", "0");

			res.setContentLength((int)file.length());

			bof = new BufferedOutputStream(res.getOutputStream());
			bif = new BufferedInputStream(fis);

			int data; 
			byte b[] = new byte[this.maxSize];
			
			while((data = bif.read(b, 0, this.maxSize)) != -1) { 
				bof.write(b, 0, data); 
				bof.flush();
			} 
		} catch( Exception ex ) {
			logger.error("filedownload error");
			ex.printStackTrace();
			throw new ProcessException("E011", ex);
		} finally {
			try {
				if( bif != null ) bif.close();
				if( bof != null ) bof.close();
			} catch( IOException e ) {
				logger.error("file close error");
				e.printStackTrace();
			}
		}
	}
}
