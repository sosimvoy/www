package com.etax.helper;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.*;
import com.etax.framework.exception.*;

public class CommandHelper {

	private static Logger logger = Logger.getLogger(CommandHelper.class);	// log4j ¼³Á¤

	private static ServletContext application;
	private String cmdName; 

	public CommandHelper(String cmdName) {
		this.cmdName = cmdName;
	}

	public CommandHelper() {
	}

	public static void setServletContext(ServletContext application) {
		CommandHelper.application = application;
	}

	public void setCommand(String cmdName) {
		this.cmdName = cmdName;
	}

	public void executeCommand(HttpServletRequest req, HttpServletResponse res) {
		if( CommandHelper.application == null ) {
			logger.error("Application(Servlet Context) is null.");
			throw new ProcessException("ServletContext is not setted, Initialize servlet.");
		}
		CommandFactory factory = CommandFactory.getInstance();
		Command command = factory.createCommand(this.cmdName);
		command.init(CommandHelper.application);
		command.execute(req,res);
	}

}
