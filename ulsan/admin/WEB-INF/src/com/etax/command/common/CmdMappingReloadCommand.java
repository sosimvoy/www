package com.etax.command.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

import com.etax.config.ETaxConfig;
import com.etax.framework.command.*;

public class CmdMappingReloadCommand extends BaseCommand {
	
	// Connection 이 필요없는 경우는 Command 의 execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		String commandXmlFileName = ETaxConfig.getString("command_list");
		CommandMapper mapper = new CommandMapper(commandXmlFileName);
		CommandConfiguration cmdConfig = mapper.doMapping();
		CommandFactory factory = CommandFactory.getInstance();
		factory.setConfiguration(cmdConfig);
		
	}

	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request,
						HttpServletResponse response,
						Connection conn) throws SQLException {
		//dummy
	}

}