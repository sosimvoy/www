package com.etax.command.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

import com.etax.config.ETaxConfig;
import com.etax.framework.command.*;

public class CmdMappingReloadCommand extends BaseCommand {
	
	// Connection �� �ʿ���� ���� Command �� execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		String commandXmlFileName = ETaxConfig.getString("command_list");
		CommandMapper mapper = new CommandMapper(commandXmlFileName);
		CommandConfiguration cmdConfig = mapper.doMapping();
		CommandFactory factory = CommandFactory.getInstance();
		factory.setConfiguration(cmdConfig);
		
	}

	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request,
						HttpServletResponse response,
						Connection conn) throws SQLException {
		//dummy
	}

}