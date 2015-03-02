package com.etax.framework.command;

import org.apache.log4j.Logger;

import com.etax.framework.exception.ProcessException;

public class CommandFactory {

	private static Logger logger = Logger.getLogger(CommandFactory.class);	// log4j ����
	
	private static CommandFactory factory = new CommandFactory();

	private CommandConfiguration commandConfig;

	public static CommandFactory getInstance() {
		return factory;
	}

	private CommandFactory() {

	}

	public void setConfiguration(CommandConfiguration commandConfig) {
		this.commandConfig = commandConfig;
	}

	public Command createCommand(String cmd) {
		Command command = this.commandConfig.getCommand(cmd);
		if( command == null ) {
			logger.error("*********************************************");
			logger.error("[Factory] Not Found Matching Command Error");
			logger.error("*********************************************");
			throw new ProcessException("[Factory] Not Found Matching Command");
		}
		return command;
	}

	/* cmd �� ���� redirectPage �� �����Ѵ� ������� null ���� */
	public String createRedirectPage(String cmd) {
		String redirectPage = this.commandConfig.getRedirectPage(cmd);
		return redirectPage;
	}

}