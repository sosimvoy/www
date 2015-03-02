package com.etax.framework.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;

public interface Command {
	
	public void init(ServletContext application);

	public void execute(HttpServletRequest request, HttpServletResponse response);
}
