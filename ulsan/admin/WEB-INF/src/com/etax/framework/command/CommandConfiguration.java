package com.etax.framework.command;

import java.util.HashMap;
import org.apache.log4j.Logger;


@SuppressWarnings("serial")
public class CommandConfiguration implements java.io.Serializable  {

	private static Logger logger = Logger.getLogger(CommandConfiguration.class);	// log4j ¼³Á¤

	private HashMap<String,CommandInfo> commandInfoMap = new HashMap<String,CommandInfo>();

	public CommandConfiguration() {

	}

	public void addCommandInfo(CommandInfo cmdInfo) {
		this.commandInfoMap.put(cmdInfo.getName(), cmdInfo);
	}

	public CommandInfo getCommandInfo(String commandName) {
		return this.commandInfoMap.get(commandName);
	}

	public Command getCommand(String commandName) {
		CommandInfo commandInfo = this.getCommandInfo(commandName);
		try {
			String className = commandInfo.getType();
			Class<?> commandClass = Class.forName(className);
			Command newCommand = (Command)commandClass.newInstance();
			logger.info("Class Name => "+ commandClass.getName());
			return newCommand;
		} catch(Exception ex) {
			logger.error("CommandConfiguration getCommand() error");
			ex.printStackTrace();
			return null;
		}
	}

	public String getRedirectPage(String commandName) {
		CommandInfo commandInfo = getCommandInfo(commandName);
		return commandInfo.getRedirect();
	}


}