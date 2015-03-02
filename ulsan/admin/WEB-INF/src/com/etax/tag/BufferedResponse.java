/*
 * @(#)BufferedResponse.java
 * Copyright 2002-2003 Cloud9, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Cloud9, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.etax.tag;

import java.io.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

/**
 * Implements the HttpServletResponse to access HTTP headers and cookies.<p>
 *
 * @version 1.0, 2003.06.01
 */
public class BufferedResponse extends javax.servlet.http.HttpServletResponseWrapper {

	private HttpServletResponse response;

	private StringWriter sw = new StringWriter();
	private ByteArrayOutputStream bos = new ByteArrayOutputStream();
	private ServletOutputStream sos;

	private boolean isWriterUsed;
	private boolean isStreamUsed;

	/**
	 * constructor<p>
	 */
	public BufferedResponse(HttpServletResponse response) {
		super(response);

		if(response == null)
			throw new IllegalArgumentException("Response cannot be null");
		
		this.response = response;

		sos = new ServletOutputStream() {
			public void write(int b) throws IOException {
				bos.write(b);
			}
		};
	}

	/**
	 * Returns a PrintWriter object that can send character text to the client
	 */
	public PrintWriter getWriter() {
		if(isStreamUsed) {
			throw new IllegalStateException("IMPORT_ILLEGAL_STREAM");
		} else {
			isWriterUsed = true;
			return new PrintWriter(sw);
		}
	}

	/**
	 * Returns a ServletOutputStream suitable for writing binary data in the response. 
	 */
	public ServletOutputStream getOutputStream() {
		if(isWriterUsed) {
			throw new IllegalStateException("IMPORT_ILLEGAL_WRITER");
		} else {
			isStreamUsed = true;
			return sos;
		}
	}
	
	/**
	 * Returns character text to the client 
	 */
	public String getString() throws UnsupportedEncodingException {
		if(isWriterUsed) return sw.toString();
		if(isStreamUsed) return bos.toString(response.getCharacterEncoding());
		return "";
	}
}